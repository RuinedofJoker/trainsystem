package org.hnxxxy.rg1b.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.hnxxxy.rg1b.domain.TrainCity;
import org.hnxxxy.rg1b.domain.TrainInfoRecord;
import org.hnxxxy.rg1b.domain.TrainStationInfo;
import org.hnxxxy.rg1b.domain.TrainTrips;
import org.hnxxxy.rg1b.domain.dto.Ticket;
import org.hnxxxy.rg1b.mapper.TrainCityMapper;
import org.hnxxxy.rg1b.mapper.TrainInfoRecordMapper;
import org.hnxxxy.rg1b.mapper.TrainStationInfoMapper;
import org.hnxxxy.rg1b.mapper.TrainTripsMapper;
import org.hnxxxy.rg1b.utils.BeanUtils;
import org.hnxxxy.rg1b.utils.ticketutils.PurposeCode;
import org.hnxxxy.rg1b.utils.ticketutils.TicketUtilsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 定时任务里面用： trainInfoInjectTask.inject
 */
@Component("tit")
@Slf4j
public class TrainInfoInjectTask {

    @Autowired
    TrainCityMapper trainCityMapper;
    @Autowired
    TrainStationInfoMapper trainStationInfoMapper;
    @Autowired
    TrainTripsMapper trainTripsMapper;
    @Autowired
    TrainInfoRecordMapper trainInfoRecordMapper;

    //第一个字符有GC-高铁/城际/复兴号，D-动车，Z-直达，T-特快，K-快速,其他(1-8)
    private static volatile String fetchTrainType = "CGDZTK12345678";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static AtomicReference<String> taskState = new AtomicReference<>("当前无任务正在执行");
    private static ReentrantLock taskLock = new ReentrantLock();

    /**
     * 车站信息注入（一次性）
     */
    public void insertCityInfo(){
        Map<String, String> cityMap = TicketUtilsApi.getAllStation();
        for (String cityName:cityMap.keySet()){
            TrainCity trainCity = new TrainCity();
            trainCity.setCityName(cityName);
            trainCity.setCityNo(cityMap.get(cityName));
            trainCityMapper.insertTrainCity(trainCity);
        }
    }

    /**
     * 删除过期车次
     */
    public void deleteTrips(){

        Integer curTime = Integer.parseInt(dateFormat.format(new Date()));
        LambdaQueryWrapper<TrainTrips> deleteTrainTripsInfoWrapper = new LambdaQueryWrapper<>();
        deleteTrainTripsInfoWrapper.lt(TrainTrips::getTrainDate,curTime);
        LambdaQueryWrapper<TrainStationInfo> deleteTrainStationInfoWrapper = new LambdaQueryWrapper<>();
        deleteTrainStationInfoWrapper.lt(TrainStationInfo::getTrainDate,curTime);

        //删除train_trips和train_station_info表中train_date小于curTime的
        trainTripsMapper.delete(deleteTrainTripsInfoWrapper);
        trainStationInfoMapper.delete(deleteTrainStationInfoWrapper);
    }

    //第一个字符有GC-高铁/城际/复兴号，D-动车，Z-直达，T-特快，K-快速,其他(1-8)
    /**
     * 车次信息注入(从今天开始到指定的日期)
     */
    public String insertTripsUntilDesignateDay(String designateDate, Boolean isConcurrent){

        designateDate = designateDate.replace("-", "");
        AtomicReference<String> currTaskStatu = new AtomicReference<>("");
        AtomicReference<String> designateDay = new AtomicReference<>(designateDate);

        try {
            Calendar currCalendar = Calendar.getInstance();
            currCalendar.setTime(new Date());
            Calendar designateCalendar = Calendar.getInstance();
            designateCalendar.setTime(dateFormat.parse(designateDate));
            Calendar timeoutDate = Calendar.getInstance();
            timeoutDate.setTime(new Date());
            timeoutDate.add(Calendar.DATE, 15);

            //非法日期(输入的指定时间比今天小)
            if (designateCalendar.before(currCalendar) || designateCalendar.after(timeoutDate)){
                return "输入日期非法";
            }

            Thread thread = new Thread(() -> {
                if (!taskLock.tryLock()){
                    while (true){
                        String prevCurrTaskStatu = currTaskStatu.get();
                        String nextCurrTaskStatu = "当前有任务正在执行";
                        if (currTaskStatu.compareAndSet(prevCurrTaskStatu, nextCurrTaskStatu)){
                            break;
                        }
                    }
                    return;
                }
                try {
                    while (true){
                        String prevCurrTaskStatu = currTaskStatu.get();
                        String nextCurrTaskStatu = "任务发布成功";
                        if (currTaskStatu.compareAndSet(prevCurrTaskStatu, nextCurrTaskStatu)){
                            break;
                        }
                    }
                    while (true){
                        String prevTaskState = taskState.get();
                        String nextTaskState = "获取到"+designateDay.get()+"日为止的数据任务正在执行";
                        if (taskState.compareAndSet(prevTaskState, nextTaskState)){
                            break;
                        }
                    }
                    while (true){
                        String lastExec = insertTripsByDesignateDate(dateFormat.format(currCalendar.getTime()), false);
                        currTaskStatu.set(lastExec);
                        if (currCalendar.equals(designateCalendar)){
                            break;
                        }
                        currCalendar.add(Calendar.DATE, 1);
                    }
                }finally {
                    taskLock.unlock();
                    while (true){
                        String prevTaskState = taskState.get();
                        String nextTaskState = "当前无任务正在执行";
                        if (taskState.compareAndSet(prevTaskState, nextTaskState)){
                            break;
                        }
                    }
                }
            });
            if (isConcurrent){
                thread.start();
            }else {
                thread.run();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return currTaskStatu.get();
    }

    /**
     * 获取指定日期的数据
     * @param designateDate 获取designateDate号的数据
     */
    public String insertTripsByDesignateDate(String designateDate, Boolean isConcurrent){

        AtomicReference<String> currTaskStatu = new AtomicReference<>("");

        Thread thread = new Thread(() -> {
            if (!taskLock.tryLock()){
                while (true){
                    String prevCurrTaskStatu = currTaskStatu.get();
                    String nextCurrTaskStatu = "当前有任务正在执行";
                    if (currTaskStatu.compareAndSet(prevCurrTaskStatu, nextCurrTaskStatu)){
                        break;
                    }
                }
                return;
            }
            try {
                while (true){
                    String prevCurrTaskStatu = currTaskStatu.get();
                    String nextCurrTaskStatu = "任务发布成功";
                    if (currTaskStatu.compareAndSet(prevCurrTaskStatu, nextCurrTaskStatu)){
                        break;
                    }
                }
                while (true){
                    String prevTaskState = taskState.get();
                    String nextTaskState = "获取"+designateDate+"的数据任务正在执行";
                    if (taskState.compareAndSet(prevTaskState, nextTaskState)){
                        break;
                    }
                }
                char[] chars = fetchTrainType.toCharArray();
                List<String> firstStrs = new ArrayList<>();
                for (char s:chars){
                    firstStrs.add(s+"");
                }

                for (int i = 0; i < firstStrs.size(); i++){
                    try {
                        coreInjectByTrainType(dateFormat.parse(designateDate.replace("-", "")), firstStrs, i);
                    }catch (Exception e){}
                }
                try {
                    //每查完一天停30秒
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                while (true){
                    String prevCurrTaskStatu = currTaskStatu.get();
                    String nextCurrTaskStatu = "任务执行完成";
                    if (currTaskStatu.compareAndSet(prevCurrTaskStatu, nextCurrTaskStatu)){
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                while (true){
                    String prevCurrTaskStatu = currTaskStatu.get();
                    String nextCurrTaskStatu = "任务执行失败";
                    if (currTaskStatu.compareAndSet(prevCurrTaskStatu, nextCurrTaskStatu)){
                        break;
                    }
                }
            }finally {
                taskLock.unlock();
                while (true){
                    String prevTaskState = taskState.get();
                    String nextTaskState = "当前无任务正在执行";
                    if (taskState.compareAndSet(prevTaskState, nextTaskState)){
                        break;
                    }
                }
            }
        });
        if (isConcurrent){
            thread.start();
        }else {
            thread.run();
        }
        return currTaskStatu.get();
    }

    /**
     * 当前类核心的查询并存入数据库方法
     * @param designateDate 指定日期
     * @param firstStrs 车次类型首字母数组
     * @param currType 指定车次类型首字母
     */
    @Transactional
    public void coreInjectByTrainType(Date designateDate, List<String> firstStrs, int currType){

        log.info("开始获取"+dateFormat.format(designateDate)+"日列车类别为"+firstStrs.get(currType)+"的数据");
        System.out.println("开始获取"+dateFormat.format(designateDate)+"日列车类别为"+firstStrs.get(currType)+"的数据");

        //根据日期和车次类型首字母查询车次
        //第一个字符有G-高铁/城际/复兴号，D-动车，Z-直达，T-特快，K-快速,其他(1-8)
        List<Ticket> tickets = TicketUtilsApi.getDayAllTickets(firstStrs.get(currType), designateDate);
        for (Ticket ticket:tickets){
            TrainTrips trainTrips = new TrainTrips();
            BeanUtils.copyProperties(ticket,trainTrips);
            trainTrips.setTrainDate(Integer.parseInt(ticket.getTrainDate()));
            trainTrips.setTotalNum(ticket.getTotalNum());
            String from = trainCityMapper.selectNoByName(trainTrips.getFromStationName()).getCityNo();
            String to = trainCityMapper.selectNoByName(trainTrips.getToStationName()).getCityNo();

            //获取车次详情
            List<Ticket> trainInfo = TicketUtilsApi.getTrainInfo(trainTrips.getTrainNo(), from, to, dateFormat.format(designateDate));
            List<TrainStationInfo> stationInfos = new ArrayList<>();
            //将该车次存入数据库
            trainTripsMapper.insertTrainTripsInfo(trainTrips);

            //遍历当前车次的站点信息存入数据库
            for (int i = 0; i < trainInfo.size(); i++){
                TrainStationInfo stationInfo = new TrainStationInfo();
                stationInfo.setTrainNo(trainInfo.get(i).getTrainNo());
                stationInfo.setCityName(trainInfo.get(i).getStationName());
                stationInfo.setTotalNum(trainInfo.get(i).getStationNo());
                if (!ObjectUtils.isEmpty(trainInfo.get(i).getStartTime())){
                    try {
                        stationInfo.setStayTime(timeFormat.parse(trainInfo.get(i).getStartTime()));
                    } catch (ParseException e) {}
                }
                //当arriveTime为空或为空字符串时可能是第一站，直接让arriveTime等于stayTime
                if (!ObjectUtils.isEmpty(trainInfo.get(i).getArriveTime())){
                    try {
                        stationInfo.setArriveTime(timeFormat.parse(trainInfo.get(i).getArriveTime()));
                    } catch (ParseException e) {
                        try {
                            stationInfo.setArriveTime(timeFormat.parse(trainInfo.get(i).getStartTime()));
                        } catch (ParseException ex) {}
                    }
                }else {
                    try {
                        stationInfo.setArriveTime(timeFormat.parse(trainInfo.get(i).getStartTime()));
                    } catch (ParseException e) {}
                }
                //给一个不会重复的随机id
                stationInfo.setStationTrainId(UUID.randomUUID().toString());
                try {
                    if (i > 0 && timeFormat.parse(trainInfo.get(i).getStartTime()).compareTo(timeFormat.parse(trainInfo.get(i-1).getStartTime())) < 0){
                        //当当前站的时间比上一站的时间还早，可能是到了第二天，日期添加一天
                        Calendar c = Calendar.getInstance();
                        c.setTime(dateFormat.parse(trainInfo.get(i-1).getTrainDate()));
                        c.add(Calendar.DATE, 1);
                        trainInfo.get(i).setTrainDate(dateFormat.format(c.getTime()));
                    }
                }catch (Exception e){}
                stationInfo.setTrainDate(Integer.parseInt(trainInfo.get(i).getTrainDate()));
                stationInfos.add(stationInfo);
            }
            for (TrainStationInfo trainStationInfo:stationInfos){
                trainStationInfoMapper.insertTrainStationInfo(trainStationInfo);
            }
        }
        insertSeatTypes(designateDate, firstStrs, currType);

        //数据库里的记录添加上当前的日期和列车类别
        //先查看库里有没有这一日的记录
        LambdaQueryWrapper<TrainInfoRecord> currDateQueryWrapper = new LambdaQueryWrapper<>();
        currDateQueryWrapper
                .eq(TrainInfoRecord::getRecordDate, Integer.parseInt(dateFormat.format(designateDate)));

        TrainInfoRecord trainInfoRecord = trainInfoRecordMapper.selectOne(currDateQueryWrapper);

        //没有该日的记录
        if (ObjectUtils.isEmpty(trainInfoRecord)){
            trainInfoRecord = new TrainInfoRecord();
            trainInfoRecord.setRecordDate(Integer.parseInt(dateFormat.format(designateDate)));
            trainInfoRecord.setRecordTrainType(firstStrs.get(currType));
            trainInfoRecord.setIsEffective(1);

            //插入该条
            trainInfoRecordMapper.insert(trainInfoRecord);
        }

        //没有包含该列车类别的记录
        if (trainInfoRecord.getRecordTrainType().contains(firstStrs.get(currType))){
            trainInfoRecord.setRecordTrainType(trainInfoRecord.getRecordTrainType()+firstStrs.get(currType));

            trainInfoRecordMapper.update(trainInfoRecord, currDateQueryWrapper);
        }

        //打印日志
        log.info("获取"+dateFormat.format(designateDate)+"日列车类别为"+firstStrs.get(currType)+"的数据成功");
        System.out.println("获取"+dateFormat.format(designateDate)+"日列车类别为"+firstStrs.get(currType)+"的数据成功");

        try {
            //每查完一个类别停30秒
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询trips的seatTypes(车票类型)
     * @param designateDate 指定日期
     * @param firstStrs 车次类型首字母数组
     * @param currType 指定车次类型首字母
     */
    public void insertSeatTypes(Date designateDate, List<String> firstStrs, int currType){

        LambdaQueryWrapper<TrainTrips> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(TrainTrips::getTrainDate, designateDate)
                .like(TrainTrips::getStationTrainCode, firstStrs.get(currType)+"%");

        List<TrainTrips> trainTrips = trainTripsMapper.selectList(queryWrapper);

        for (TrainTrips currTrainTrips : trainTrips){

            try {
                if (ObjectUtils.isEmpty(currTrainTrips.getSeatTypes())){
                    String fromCityNo = trainCityMapper.selectNoByName(currTrainTrips.getFromStationName()).getCityNo();
                    String toCityNo = trainCityMapper.selectNoByName(currTrainTrips.getToStationName()).getCityNo();
                    String ticketDate = new StringBuffer(currTrainTrips.getTrainDate().toString()).insert(6,"-").insert(4,"-").toString();
                    List<Ticket> tickets = TicketUtilsApi.getTickets(ticketDate, fromCityNo, toCityNo, PurposeCode.PurposeCode_ADULT);
                    for (int i = 0; i < tickets.size(); i++){
                        try {
                            TrainTrips tempTrips = new TrainTrips();
                            tempTrips.setTrainNo(tickets.get(i).getTrainNo().replace(" ",""));
                            tempTrips.setSeatTypes(tickets.get(i).getSeatTypes().replace(" ",""));
                            tempTrips.setTrainDate(Integer.parseInt(tickets.get(i).getTrainDate().replace("-","").replace(" ","")));
                            tempTrips.setGjrw(tickets.get(i).getGjrw());//高级软卧
                            tempTrips.setRw(tickets.get(i).getRw());//软卧
                            tempTrips.setWz(tickets.get(i).getWz());//无座
                            tempTrips.setYw(tickets.get(i).getYw());//硬卧
                            tempTrips.setYz(tickets.get(i).getYz());//硬座
                            tempTrips.setEd(tickets.get(i).getEd());//二等座
                            tempTrips.setYd(tickets.get(i).getYd());//一等座
                            tempTrips.setTd(tickets.get(i).getTd());//商务座，特等座

                            trainTripsMapper.updateSeatTypes(tempTrips);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }catch (Exception e){}
        }
    }

    public void setFetchTrainType(String fetchTrainType) {
        this.fetchTrainType = fetchTrainType;
    }
    public String getFetchTrainType(){
        return fetchTrainType;
    }

    /**
     * 获取有效的记录
     * @return
     */
    public List<TrainInfoRecord> getTrainInfoRecord(){

        LambdaQueryWrapper<TrainInfoRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(TrainInfoRecord::getIsEffective, 1)
                .orderBy(true, true, TrainInfoRecord::getRecordDate);

        List<TrainInfoRecord> trainInfoRecords = trainInfoRecordMapper.selectList(queryWrapper);
        return trainInfoRecords;
    }
}


