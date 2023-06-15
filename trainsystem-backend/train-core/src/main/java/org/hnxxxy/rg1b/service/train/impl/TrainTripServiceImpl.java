package org.hnxxxy.rg1b.service.train.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.domain.TrainStationInfo;
import org.hnxxxy.rg1b.domain.TrainTrips;
import org.hnxxxy.rg1b.domain.dto.Ticket;
import org.hnxxxy.rg1b.domain.dto.TrainTripsPage;
import org.hnxxxy.rg1b.domain.vo.TrainTripsDetailVo;
import org.hnxxxy.rg1b.domain.vo.TrainTripsPrice;
import org.hnxxxy.rg1b.domain.vo.TrainTripsVo;
import org.hnxxxy.rg1b.mapper.TrainCityMapper;
import org.hnxxxy.rg1b.mapper.TrainStationInfoMapper;
import org.hnxxxy.rg1b.mapper.TrainTripsMapper;
import org.hnxxxy.rg1b.service.train.ITrainTripService;
import org.hnxxxy.rg1b.utils.BeanUtils;
import org.hnxxxy.rg1b.utils.ticketutils.TicketUtilsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TrainTripServiceImpl implements ITrainTripService {

    @Autowired
    private TrainTripsMapper trainTripsMapper;
    @Autowired
    private TrainStationInfoMapper trainStationInfoMapper;
    @Autowired
    private TrainCityMapper trainCityMapper;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    /**
     * 分页查询单程的车次信息
     * @param trainTripsVo
     * @return
     */
    @Override
    @Cacheable(value = "trainTrips",
            key = "#trainTripsVo.fromCity+'_'+#trainTripsVo.fromDate+" +
                    "'_'+#trainTripsVo.toCity+" +
                    "'_'+#trainTripsVo.departureTime+" +
                    "'_'+#trainTripsVo.trainType+" +
                    "'_'+#trainTripsVo.pageNo+'_'+#trainTripsVo.pageSize")
    public PageVo<TrainTripsDetailVo> selectOneWayTrips(TrainTripsVo trainTripsVo) {

        List<TrainTripsDetailVo> resultList = new ArrayList<>();

        //给城市加上模糊匹配
        String fromCityFuzzy = "%";
        String toCityFuzzy = "%";
        char[] fromChars = trainTripsVo.getFromCity().toCharArray();
        char[] toChars = trainTripsVo.getToCity().toCharArray();
        for (char fromChar : fromChars){
            fromCityFuzzy = fromCityFuzzy + fromChar + "%";
        }
        for (char toChar : toChars){
            toCityFuzzy = toCityFuzzy + toChar + "%";
        }

        //添加查询的列车类型
        if (trainTripsVo.getTrainType() != null){
            if (trainTripsVo.getTrainType().size() != 0){
                for (int i = 0; i< trainTripsVo.getTrainType().size(); i++){
                    trainTripsVo.getTrainType().set(i, trainTripsVo.getTrainType().get(i) + "%");
                }
            }
        }else {
            trainTripsVo.setTrainType(new ArrayList<>());
            trainTripsVo.getTrainType().add("%");
        }

        //列车trips的查询实体类
        TrainTripsPage selectFromTrainTrips = new TrainTripsPage();
        selectFromTrainTrips.setTrainType(trainTripsVo.getTrainType());
        selectFromTrainTrips.setDepartureTimeBegin(trainTripsVo.getDepartureTime().split("-")[0]);
        selectFromTrainTrips.setDepartureTimeEnd(trainTripsVo.getDepartureTime().split("-")[1]);
        selectFromTrainTrips.setFromStationName(fromCityFuzzy);
        selectFromTrainTrips.setToStationName(toCityFuzzy);
        selectFromTrainTrips.setTrainDate(Integer.parseInt(trainTripsVo.getFromDate().replace("-", "")));
        selectFromTrainTrips.setPageNo(trainTripsVo.getPageNo());
        selectFromTrainTrips.setPageSize(trainTripsVo.getPageSize());

        //查询到列车trips
        List<TrainTrips> fromTrainTrips = trainTripsMapper.selectOneWayPage(selectFromTrainTrips);
        //查询该条件下能查到的总条目数
        int fromTrainTripsCount = trainTripsMapper.selectOneWayPageCount(selectFromTrainTrips);
        //查询当前的起点站与终点站信息
        for (TrainTrips currFromTrip : fromTrainTrips){
            LambdaQueryWrapper<TrainStationInfo> fromQueryWrapper = new LambdaQueryWrapper();
            LambdaQueryWrapper<TrainStationInfo> toQueryWrapper = new LambdaQueryWrapper();
            fromQueryWrapper
                    .eq(TrainStationInfo::getTrainNo, currFromTrip.getTrainNo())
                    .like(TrainStationInfo::getCityName, fromCityFuzzy);
            toQueryWrapper
                    .eq(TrainStationInfo::getTrainNo, currFromTrip.getTrainNo())
                    .like(TrainStationInfo::getCityName, toCityFuzzy);
            TrainStationInfo fromTripsInfo = trainStationInfoMapper.selectOne(fromQueryWrapper);
            TrainStationInfo toTripsInfo = trainStationInfoMapper.selectOne(toQueryWrapper);
            TrainTripsDetailVo currTripsDetailVo = new TrainTripsDetailVo();
            currTripsDetailVo.setStationTrainCode(currFromTrip.getStationTrainCode());
            currTripsDetailVo.setFromStationName(fromTripsInfo.getCityName());
            currTripsDetailVo.setToStationName(toTripsInfo.getCityName());
            currTripsDetailVo.setFromTime(timeFormat.format(fromTripsInfo.getStayTime()));
            currTripsDetailVo.setToTime(timeFormat.format(toTripsInfo.getArriveTime()));
            if (toTripsInfo.getArriveTime().getTime() - fromTripsInfo.getStayTime().getTime() > 0){
                currTripsDetailVo.setElapseTime(timeFormat.format(toTripsInfo.getArriveTime().getTime() - fromTripsInfo.getStayTime().getTime()));
                currTripsDetailVo.setIsToday("当日到达");
            }else {
                currTripsDetailVo.setElapseTime(timeFormat.format(fromTripsInfo.getStayTime().getTime() - toTripsInfo.getArriveTime().getTime()));
                currTripsDetailVo.setIsToday("次日到达");
            }
            resultList.add(currTripsDetailVo);
        }

        PageVo<TrainTripsDetailVo> pageVo = new PageVo<>(fromTrainTripsCount, trainTripsVo.getPageNo(), trainTripsVo.getPageSize(),resultList);

        return pageVo;
    }

    /**
     * 分页查询往返的车次信息
     * @param trainTripsVo
     * @return
     */
    @Override
    @Cacheable(value = "trainTrips",
            key = "#trainTripsVo.fromCity+'_'+#trainTripsVo.fromDate+" +
                    "'_'+#trainTripsVo.toCity+'_'+#trainTripsVo.toDate+" +
                    "'_'+#trainTripsVo.departureTime+" +
                    "'_'+#trainTripsVo.trainType+" +
                    "'_'+#trainTripsVo.pageNo+'_'+#trainTripsVo.pageSize")
    public PageVo<TrainTripsDetailVo> selectRoundTripTrips(TrainTripsVo trainTripsVo) {

        List<TrainTripsDetailVo> resultList = new ArrayList<>();

        String fromCityFuzzy = "%";
        String toCityFuzzy = "%";
        char[] fromChars = trainTripsVo.getFromCity().toCharArray();
        char[] toChars = trainTripsVo.getToCity().toCharArray();
        for (char fromChar : fromChars){
            fromCityFuzzy = fromCityFuzzy + fromChar + "%";
        }
        for (char toChar : toChars){
            toCityFuzzy = toCityFuzzy + toChar + "%";
        }

        if (trainTripsVo.getTrainType() != null){
            if (trainTripsVo.getTrainType().size() != 0){
                for (int i = 0; i< trainTripsVo.getTrainType().size(); i++){
                    trainTripsVo.getTrainType().set(i, trainTripsVo.getTrainType().get(i) + "%");
                }
            }
        }else {
            trainTripsVo.setTrainType(new ArrayList<>());
            trainTripsVo.getTrainType().add("%");
        }

        TrainTripsPage selectFromTrainTrips = new TrainTripsPage();
        selectFromTrainTrips.setTrainType(trainTripsVo.getTrainType());
        selectFromTrainTrips.setDepartureTimeBegin(trainTripsVo.getDepartureTime().split("-")[0]);
        selectFromTrainTrips.setDepartureTimeEnd(trainTripsVo.getDepartureTime().split("-")[1]);
        selectFromTrainTrips.setFromStationName(fromCityFuzzy);
        selectFromTrainTrips.setToStationName(toCityFuzzy);
        selectFromTrainTrips.setTrainDate(Integer.parseInt(trainTripsVo.getFromDate().replace("-", "")));
        selectFromTrainTrips.setPageNo(trainTripsVo.getPageNo());
        selectFromTrainTrips.setPageSize(trainTripsVo.getPageSize() / 2);

        TrainTripsPage selectToTrainTrips = new TrainTripsPage();
        selectToTrainTrips.setTrainType(trainTripsVo.getTrainType());
        selectToTrainTrips.setDepartureTimeBegin(trainTripsVo.getDepartureTime().split("-")[0]);
        selectToTrainTrips.setDepartureTimeEnd(trainTripsVo.getDepartureTime().split("-")[1]);
        selectToTrainTrips.setFromStationName(toCityFuzzy);
        selectToTrainTrips.setToStationName(fromCityFuzzy);
        selectToTrainTrips.setTrainDate(Integer.parseInt(trainTripsVo.getToDate().replace("-", "")));
        selectToTrainTrips.setPageNo(trainTripsVo.getPageNo());
        selectToTrainTrips.setPageSize(trainTripsVo.getPageSize() / 2);

        List<TrainTrips> fromTrainTrips = trainTripsMapper.selectOneWayPage(selectFromTrainTrips);
        //查询该条件下能查到的总条目数
        int fromTrainTripsCount = trainTripsMapper.selectOneWayPageCount(selectFromTrainTrips);
        for (TrainTrips currFromTrip : fromTrainTrips){
            LambdaQueryWrapper<TrainStationInfo> fromQueryWrapper = new LambdaQueryWrapper();
            LambdaQueryWrapper<TrainStationInfo> toQueryWrapper = new LambdaQueryWrapper();
            fromQueryWrapper
                    .eq(TrainStationInfo::getTrainNo, currFromTrip.getTrainNo())
                    .like(TrainStationInfo::getCityName, fromCityFuzzy);
            toQueryWrapper
                    .eq(TrainStationInfo::getTrainNo, currFromTrip.getTrainNo())
                    .like(TrainStationInfo::getCityName, toCityFuzzy);
            TrainStationInfo fromTripsInfo = trainStationInfoMapper.selectOne(fromQueryWrapper);
            TrainStationInfo toTripsInfo = trainStationInfoMapper.selectOne(toQueryWrapper);
            TrainTripsDetailVo currTripsDetailVo = new TrainTripsDetailVo();
            currTripsDetailVo.setStationTrainCode(currFromTrip.getStationTrainCode());
            currTripsDetailVo.setFromStationName(fromTripsInfo.getCityName());
            currTripsDetailVo.setToStationName(toTripsInfo.getCityName());
            currTripsDetailVo.setFromTime(timeFormat.format(fromTripsInfo.getStayTime()));
            currTripsDetailVo.setToTime(timeFormat.format(toTripsInfo.getArriveTime()));
            if (toTripsInfo.getArriveTime().getTime() - fromTripsInfo.getStayTime().getTime() > 0){
                currTripsDetailVo.setElapseTime(timeFormat.format(toTripsInfo.getArriveTime().getTime() - fromTripsInfo.getStayTime().getTime()));
                currTripsDetailVo.setIsToday("当日到达");
            }else {
                currTripsDetailVo.setElapseTime(timeFormat.format(fromTripsInfo.getStayTime().getTime() - toTripsInfo.getArriveTime().getTime()));
                currTripsDetailVo.setIsToday("次日到达");
            }
            resultList.add(currTripsDetailVo);
        }
        List<TrainTrips> toTrainTrips = trainTripsMapper.selectOneWayPage(selectToTrainTrips);
        fromTrainTripsCount += trainTripsMapper.selectOneWayPageCount(selectToTrainTrips);
        for (TrainTrips currFromTrip : toTrainTrips){
            LambdaQueryWrapper<TrainStationInfo> fromQueryWrapper = new LambdaQueryWrapper();
            LambdaQueryWrapper<TrainStationInfo> toQueryWrapper = new LambdaQueryWrapper();
            fromQueryWrapper
                    .eq(TrainStationInfo::getTrainNo, currFromTrip.getTrainNo())
                    .like(TrainStationInfo::getCityName, toCityFuzzy);
            toQueryWrapper
                    .eq(TrainStationInfo::getTrainNo, currFromTrip.getTrainNo())
                    .like(TrainStationInfo::getCityName, fromCityFuzzy);
            TrainStationInfo fromTripsInfo = trainStationInfoMapper.selectOne(fromQueryWrapper);
            TrainStationInfo toTripsInfo = trainStationInfoMapper.selectOne(toQueryWrapper);
            TrainTripsDetailVo currTripsDetailVo = new TrainTripsDetailVo();
            currTripsDetailVo.setStationTrainCode(currFromTrip.getStationTrainCode());
            currTripsDetailVo.setFromStationName(fromTripsInfo.getCityName());
            currTripsDetailVo.setToStationName(toTripsInfo.getCityName());
            currTripsDetailVo.setFromTime(timeFormat.format(fromTripsInfo.getStayTime()));
            currTripsDetailVo.setToTime(timeFormat.format(toTripsInfo.getArriveTime()));
            if (toTripsInfo.getArriveTime().getTime() - fromTripsInfo.getStayTime().getTime() > 0){
                currTripsDetailVo.setElapseTime(timeFormat.format(toTripsInfo.getArriveTime().getTime() - fromTripsInfo.getStayTime().getTime()));
                currTripsDetailVo.setIsToday("当日到达");
            }else {
                currTripsDetailVo.setElapseTime(timeFormat.format(fromTripsInfo.getStayTime().getTime() - toTripsInfo.getArriveTime().getTime()));
                currTripsDetailVo.setIsToday("次日到达");
            }
            resultList.add(currTripsDetailVo);
        }

        PageVo<TrainTripsDetailVo> pageVo = new PageVo<>(fromTrainTripsCount, trainTripsVo.getPageNo(), trainTripsVo.getPageSize(),resultList);

        return pageVo;
    }

    @Override
    @Transactional
    @Cacheable(value = "trainTripsPrice", key = "#stationTrainCode+'_'+#trainDate+'_'+'fromStationName'+'_'+#toStationName+'_'+#tripType")
    public TrainTripsPrice selectTripPrice(String stationTrainCode, String trainDate, String fromStationName, String toStationName, String tripType) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        trainDate = trainDate.replace("-","");
        String nextDay = "";
        try {
            Date trainDateNextDay = dateFormat.parse(trainDate);
            Calendar c = Calendar.getInstance();
            c.setTime(trainDateNextDay);
            c.add(Calendar.DATE, 1);
            trainDateNextDay = c.getTime();
            nextDay = dateFormat.format(trainDateNextDay);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        LambdaQueryWrapper<TrainTrips> tripsQueryWrapper = new LambdaQueryWrapper<>();
        tripsQueryWrapper
                .eq(TrainTrips::getStationTrainCode, stationTrainCode)
                .eq(TrainTrips::getTrainDate, Integer.parseInt(trainDate));
        TrainTrips trainTrips = trainTripsMapper.selectOne(tripsQueryWrapper);

        LambdaQueryWrapper<TrainStationInfo> fromStationQueryWrapper = new LambdaQueryWrapper<>();
        fromStationQueryWrapper
                .eq(TrainStationInfo::getTrainNo, trainTrips.getTrainNo())
                .eq(TrainStationInfo::getTrainDate, trainDate)
                .eq(TrainStationInfo::getCityName, fromStationName);
        TrainStationInfo fromStation = trainStationInfoMapper.selectOne(fromStationQueryWrapper);

        LambdaQueryWrapper<TrainStationInfo> toStationQueryWrapper = new LambdaQueryWrapper<>();
        String finalTrainDate = trainDate;
        String finalNextDay = nextDay;
        toStationQueryWrapper
                .eq(TrainStationInfo::getTrainNo, trainTrips.getTrainNo())
                .eq(TrainStationInfo::getCityName, toStationName)
                .and(wrapper -> wrapper.eq(TrainStationInfo::getTrainDate, finalTrainDate)
                        .or().eq(TrainStationInfo::getTrainDate, finalNextDay));
        TrainStationInfo toStation = trainStationInfoMapper.selectOne(toStationQueryWrapper);

        Ticket ticket = new Ticket();
        ticket.setTrainNo(trainTrips.getTrainNo());
        ticket.setFromStationNo(fromStation.getTotalNum().toString().length() < 2 ? "0"+fromStation.getTotalNum().toString() : "0"+fromStation.getTotalNum().toString());
        ticket.setToStationNo(toStation.getTotalNum().toString().length() < 2 ? "0"+toStation.getTotalNum().toString() : "0"+toStation.getTotalNum().toString());
        ticket.setSeatTypes(trainTrips.getSeatTypes());
        ticket.setGjrw(trainTrips.getGjrw());//高级软卧
        ticket.setRw(trainTrips.getRw());//软卧
        ticket.setWz(trainTrips.getWz());//无座
        ticket.setYw(trainTrips.getYw());//硬卧
        ticket.setYz(trainTrips.getYz());//硬座
        ticket.setEd(trainTrips.getEd());//二等座
        ticket.setYd(trainTrips.getYd());//一等座
        ticket.setTd(trainTrips.getTd());//商务座，特等座
        ticket.setTrainDate(new StringBuffer(trainTrips.getTrainDate().toString()).insert(6,"-").insert(4,"-").toString());
        TicketUtilsApi.getPrice(ticket);

        TrainTripsPrice tripsPrice = new TrainTripsPrice();
        BeanUtils.copyProperties(trainTrips, tripsPrice);

        tripsPrice.setGjrwPrice(!StringUtils.isEmpty(ticket.getGjrwPrice()) ? ticket.getGjrwPrice() : 0);//高级软卧
        tripsPrice.setRwPrice(!StringUtils.isEmpty(ticket.getRwPrice()) ? ticket.getRwPrice() : 0);//软卧
        tripsPrice.setWzPrice(!StringUtils.isEmpty(ticket.getWzPrice()) ? ticket.getWzPrice() : 0);//无座
        tripsPrice.setYwPrice(!StringUtils.isEmpty(ticket.getYwPrice()) ? ticket.getYwPrice() : 0);//硬卧
        tripsPrice.setYzPrice(!StringUtils.isEmpty(ticket.getYzPrice()) ? ticket.getYzPrice() : 0);//硬座
        tripsPrice.setEdPrice(!StringUtils.isEmpty(ticket.getEdPrice()) ? ticket.getEdPrice() : 0);//二等座
        tripsPrice.setYdPrice(!StringUtils.isEmpty(ticket.getYdPrice()) ? ticket.getYdPrice() : 0);//一等座
        tripsPrice.setTdPrice(!StringUtils.isEmpty(ticket.getTdPrice()) ? ticket.getTdPrice() : 0);//商务座，特等座

        return tripsPrice;
    }
}
