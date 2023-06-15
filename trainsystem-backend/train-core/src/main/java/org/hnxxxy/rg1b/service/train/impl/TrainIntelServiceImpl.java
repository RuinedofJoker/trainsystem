package org.hnxxxy.rg1b.service.train.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.common.TransitiveBoolean;
import org.hnxxxy.rg1b.domain.dto.IntelCity;
import org.hnxxxy.rg1b.domain.RoutePath;
import org.hnxxxy.rg1b.domain.TrainStationInfo;
import org.hnxxxy.rg1b.domain.vo.OptimalRouteVo;
import org.hnxxxy.rg1b.domain.vo.TrainIntelVo;
import org.hnxxxy.rg1b.exception.IllegalTrainIntelException;
import org.hnxxxy.rg1b.mapper.*;
import org.hnxxxy.rg1b.service.train.ITrainIntelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class TrainIntelServiceImpl implements ITrainIntelService {

    @Autowired
    private TrainCityMapper trainCityMapper;
    @Autowired
    private TrainTripsMapper trainTripsMapper;
    @Autowired
    private TrainStationInfoMapper trainStationInfoMapper;
    @Autowired
    private OptimalRouteMapper optimalRouteMapper;
    @Autowired
    private RoutePathMapper routePathMapper;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");

    /**
     * 根据前端传入的用户起始站、终点站、途中必须经过的城市（可选）得到一条或多条最优路径
     * @param trainIntelVo
     * @return
     */
    @Override
    public List<OptimalRouteVo> generateOptimalRoute(TrainIntelVo trainIntelVo, Long userId) throws IllegalTrainIntelException {
        List<OptimalRouteVo> resultRoutes = new ArrayList<>();
        List<IntelCity> sortedCity = sortByTrainDate(trainIntelVo);

        //使用算法得到最优路径集合
        //直达
        OptimalRouteVo optimalPathDirect = optimalPathAlgorithmDirect(sortedCity);
        if (!optimalPathDirect.getDescription().equals("不可达")){
            resultRoutes.add(optimalPathDirect);
            //存入数据库的optimal_route表
        }
        //时间最优
        OptimalRouteVo optimalPathByTime = optimalPathAlgorithmByTime(sortedCity);
        if (!optimalPathByTime.getDescription().equals("不可达")){
            resultRoutes.add(optimalPathByTime);
            //存入数据库的optimal_route表
        }
        System.out.println(resultRoutes);
        return resultRoutes;
    }

    /**
     * 直达算法
     * @param trainCity 已经按时间排好序的目的地列表
     */
    private OptimalRouteVo optimalPathAlgorithmDirect(List<IntelCity> trainCity){
        OptimalRouteVo optimalRoute = new OptimalRouteVo();
        optimalRoute.setDescription("直达");
        optimalRoute.setRouteId(UUID.randomUUID().toString());
        List<RoutePath> paths = new ArrayList<>();
        optimalRoute.setRoutePaths(paths);

        //每两个想要到达的城市直接都进行一次最短路径优先算法
        for (int i = 0;i < trainCity.size()-1;i++){
            //迪杰斯特拉算法,两站之间的时间差做为权重
            //起始点城市名
            String currFromCity = trainCityMapper.selectNameByNo(trainCity.get(i).getCityNo()).getCityName();
            //目标点城市名
            String currToCity = trainCityMapper.selectNameByNo(trainCity.get(i+1).getCityNo()).getCityName();
            TrainStationInfo selectMapInfo = new TrainStationInfo();
            selectMapInfo.setTrainDate(Integer.parseInt(trainCity.get(i).getTrainDate()));
            selectMapInfo.setCityName(currFromCity);

            //查询出所有该站点出发的车次的站信息来组成的列表
            List<TrainStationInfo> stationInfos = trainStationInfoMapper.selectStartMap(selectMapInfo);
            //判断两站是否可达的标记
            boolean isReach = false;
            String currTrainNo = "";
            RoutePath currPath = new RoutePath();
            String recordTrainNo = "";
            paths.add(currPath);
            //遍历查询出来的stationInfos
            //记录下目前找到的最短时间到达的站点
            int leastArriveTime = 100000;
            //添加一个该车次是否先经过了起始地的标记，防止先经过目的地后经过起始地
            boolean isThroughFrom = false;
            for (TrainStationInfo currStationInfo:stationInfos){
                //查询出来是按车次和站点排好序的
                //判断当前车次是否变了
                if (!currStationInfo.getTrainNo().equals(currTrainNo)){
                    currTrainNo = currStationInfo.getTrainNo();
                    isThroughFrom = false;
                }
                if (currFromCity.equals(currStationInfo.getCityName())){
                    isThroughFrom = true;
                }
                //判断该站是否为目的地
                if (isThroughFrom && currToCity.equals(currStationInfo.getCityName())){
                    //如果是目的地则判断时间是否最短，并存入结果集
                    if (Integer.parseInt(timeFormat.format(currStationInfo.getArriveTime())) < leastArriveTime){
                        leastArriveTime = Integer.parseInt(timeFormat.format(currStationInfo.getArriveTime()));
                        currPath.setTrainDate(Integer.parseInt(trainCity.get(i).getTrainDate()));
                        currPath.setStationTrainId(currStationInfo.getStationTrainId());
                        currPath.setFromCityName(currFromCity);
                        currPath.setToCityName(currToCity);
                        currPath.setToTime(currStationInfo.getArriveTime());
                        currPath.setOrderNum(paths.size());
                        recordTrainNo = currTrainNo;
                        //如果所有站点里面没有包含到达站则不可达，之后直接返回
                        isReach = true;
                    }
                }
            }
            if (!isReach){
                //不可直达，直接返回
                paths.remove(paths.size()-1);
                optimalRoute.setDescription("不可达");
                return optimalRoute;
            }
            //查询当前最短站点的详细信息
            LambdaQueryWrapper<TrainStationInfo> queryWrapper = new LambdaQueryWrapper();
            queryWrapper
                    .eq(TrainStationInfo::getTrainNo,recordTrainNo)
                    .eq(TrainStationInfo::getCityName,currFromCity)
                    .eq(TrainStationInfo::getTrainDate,Integer.parseInt(trainCity.get(i).getTrainDate()));
            currPath.setFromTime(trainStationInfoMapper.selectOne(queryWrapper).getStayTime());
            currPath.setPathId(UUID.randomUUID().toString());
            currPath.setRouteId(optimalRoute.getRouteId());
            //存入数据库中的route_path表
        }
        return optimalRoute;
    }

    /**
     * 时间最优算法
     * @param trainCity
     */
    private OptimalRouteVo optimalPathAlgorithmByTime(List<IntelCity> trainCity) {
        //初始化结果集
        OptimalRouteVo optimalRoute = new OptimalRouteVo();
        optimalRoute.setDescription("时间最优");
        optimalRoute.setRouteId(UUID.randomUUID().toString());
        List<RoutePath> paths = new ArrayList<>();
        optimalRoute.setRoutePaths(paths);
        int order = 1;

        //每两个想要到达的城市直接都进行一次最短路径优先算法
        for (int i = 0;i < trainCity.size()-1;i++) {
            //起始点城市名
            String currFromCity = trainCityMapper.selectNameByNo(trainCity.get(i).getCityNo()).getCityName();
            //目标点城市名
            String currToCity = trainCityMapper.selectNameByNo(trainCity.get(i + 1).getCityNo()).getCityName();

            //做一个贪心算法，先查出所有经过目的地的车次，每次取最早到达的，然后判断该车次或直达或换乘是否可达
            TrainStationInfo selectToStationInfo = new TrainStationInfo();
            selectToStationInfo.setCityName(currToCity);
            selectToStationInfo.setTrainDate(Integer.parseInt(trainCity.get(i+1).getTrainDate()));
            //查询起始地最早一趟的出发时间做剪枝
            TrainStationInfo stayedTrips = new TrainStationInfo();
            stayedTrips.setCityName(currFromCity);
            stayedTrips.setTrainDate(Integer.parseInt(trainCity.get(i).getTrainDate()));
            stayedTrips = trainStationInfoMapper.selectStayedTripsTime(stayedTrips);
            //根据目的地找到所有能到的站点
            List<TrainStationInfo> arrivedTrips = trainStationInfoMapper.selectArrivedTrips(selectToStationInfo);
            //存放回溯法得到的结果集
            List<List<TrainStationInfo>> resultTrips = new ArrayList<>();
            TransitiveBoolean isArrivedByTime = new TransitiveBoolean();
            isArrivedByTime.tBoolean = false;
            //判断当前车次是否能经过起点站(需要考虑换乘)
            for (TrainStationInfo currTrip:arrivedTrips){
                //对每一条arrivedTrips,向上遍历查找
                //先selectTrainNoBeforeCurrCity找到所有TrainNo
                //再对找到的TrainNo做selectTrainNoBeforeCurrCity进行向上查找
                //用一个深度优先的递归每次找当前找到的trainNo的上一站cityName所能经过的所有TrainNo
                //结束条件找到的cityName为currFromCity或找不到
                //当找到的那条TrainStationInfo的cityName不为currFromCity且totalNum为1时表示找不到
                backtrackingFindPathByTime(currTrip, currFromCity, resultTrips, new ArrayList<>(), currTrip ,isArrivedByTime, stayedTrips);
            }

            //判断这两个城市是否可达
            if (!isArrivedByTime.tBoolean){
                //不可达直接退出
                optimalRoute.setDescription("不可达");
                break;
            }
            //找到结果集里的最短路径
            int leastTime = 100000;
            List<TrainStationInfo> tempResult = null;
            for (List<TrainStationInfo> checkedResult:resultTrips){
                if ((checkedResult.get(0).getTrainDate() - checkedResult.get(checkedResult.size()-1).getTrainDate()) < leastTime){
                    tempResult = checkedResult;
                }
            }
            //可达则添加结果集并为查询下一组城市重置标记
            for (int j = tempResult.size() - 1; j > 0; j--,order++){
                RoutePath resultPath = new RoutePath();
                resultPath.setPathId(UUID.randomUUID().toString());
                resultPath.setRouteId(optimalRoute.getRouteId());
                resultPath.setTrainDate(tempResult.get(j).getTrainDate());
                resultPath.setOrderNum(order);
                resultPath.setFromTime(tempResult.get(j).getStayTime());
                resultPath.setFromCityName(tempResult.get(j).getCityName());
                resultPath.setToTime(tempResult.get(j-1).getArriveTime());
                resultPath.setToCityName(tempResult.get(j-1).getCityName());
                resultPath.setStationTrainId(tempResult.get(j-1).getStationTrainId());
                paths.add(resultPath);
            }
            isArrivedByTime.tBoolean = false;
        }
        //存入path表

        return optimalRoute;
    }

    //用回溯法遍历所有车次找到经过目的地的车次
    private void backtrackingFindPathByTime(TrainStationInfo currTrip, String destination, List<List<TrainStationInfo>> resultTrips, List<TrainStationInfo> tempList, TrainStationInfo startInfo, TransitiveBoolean isArrivedByTime, TrainStationInfo stayCityInfo){
        //找到了
        if (destination.equals(currTrip.getCityName())){
            isArrivedByTime.tBoolean = true;
            List<TrainStationInfo> copyList = new ArrayList<>();
            copyList.add(startInfo);
            for (TrainStationInfo trainStationInfo:tempList){
                copyList.add(trainStationInfo);
            }

            resultTrips.add(copyList);
            return;
        }
        //单次路线换乘超过3次不要(剪枝)
        if (isArrivedByTime.tBoolean){
            if(tempList.size() > 3){
                return;
            }
        }

        //查询当前TrainNo所有在该站点之前的站点
        List<TrainStationInfo> beforeCityByCurrTrainNo = trainStationInfoMapper.selectTrainNoBeforeCurrCity(currTrip);
        //这两种情况表明递归到头了
        if (beforeCityByCurrTrainNo == null){
            return;
        }
        if (beforeCityByCurrTrainNo.size() == 0){
            return;
        }
        //遍历之前的站点
        for (int i = 0; i < beforeCityByCurrTrainNo.size(); i++){
            //查询经过该站点的所有没有超时的车次
            List<TrainStationInfo> tripsByCurrCity = trainStationInfoMapper.selectTripsBeforeCurrCity(beforeCityByCurrTrainNo.get(i));
            for (TrainStationInfo oneTrip:tripsByCurrCity){
                if (stayCityInfo.getTrainDate() >= oneTrip.getTrainDate() && stayCityInfo.getStayTime().compareTo(oneTrip.getStayTime()) > 0){
                    return;
                }
                //将当前车次添加到临时结果中
                tempList.add(oneTrip);
                backtrackingFindPathByTime(oneTrip, destination, resultTrips, tempList, startInfo, isArrivedByTime, stayCityInfo);
                //回溯
                tempList.remove(tempList.size()-1);
            }
        }
    }

    /**
     * 判断前端传入的路径是否按时间顺序
     * @param trainIntelVo
     * @throws IllegalTrainIntelException
     */
    private List<IntelCity> sortByTrainDate(TrainIntelVo trainIntelVo) throws IllegalTrainIntelException {
        List<IntelCity> sortedCity = new ArrayList<>();
        List<IntelCity> throughCity = trainIntelVo.getThroughCity();
        Integer fromCityDate = Integer.parseInt(trainIntelVo.getFromCity().getTrainDate());
        Integer toCityDate = Integer.parseInt(trainIntelVo.getToCity().getTrainDate());
        sortedCity.add(trainIntelVo.getFromCity());
        for (int i = 0;i < throughCity.size();i++){
            Integer currCityDate = Integer.parseInt(throughCity.get(i).getTrainDate());
            if (currCityDate > toCityDate || currCityDate < fromCityDate){
                //不合法的trainIntelVo
                throw new IllegalTrainIntelException();
            }
            if (i != 0){
                if (currCityDate < Integer.parseInt(throughCity.get(i-1).getTrainDate())){
                    //不合法的trainIntelVo
                    throw new IllegalTrainIntelException();
                }
            }
            sortedCity.add(throughCity.get(i));
        }
        sortedCity.add(trainIntelVo.getToCity());
        return sortedCity;
    }
}
