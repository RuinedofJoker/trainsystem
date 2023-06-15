package org.hnxxxy.rg1b.service.train.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.domain.TrainStationInfo;
import org.hnxxxy.rg1b.domain.TrainTrips;
import org.hnxxxy.rg1b.mapper.TrainStationInfoMapper;
import org.hnxxxy.rg1b.mapper.TrainTripsMapper;
import org.hnxxxy.rg1b.service.train.ITrainStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrainStationServiceImpl implements ITrainStationService {

    @Resource
    private TrainStationInfoMapper trainStationInfoMapper;
    @Autowired
    private TrainTripsMapper trainTripsMapper;

    @Override
    @Cacheable(value = "trainStation", key = "#stationTrainCode+'_'+#trainDate")
    public List<TrainStationInfo> selectTrainStationList(String stationTrainCode, String trainDate) {

        int trainDateInt = Integer.parseInt(trainDate);
        String trainNo = "";

        LambdaQueryWrapper<TrainTrips> tripsQueryWrapper = new LambdaQueryWrapper<>();
        tripsQueryWrapper
                .eq(TrainTrips::getStationTrainCode, stationTrainCode)
                .eq(TrainTrips::getTrainDate, trainDate);
        trainNo = trainTripsMapper.selectOne(tripsQueryWrapper).getTrainNo();

        LambdaQueryWrapper<TrainStationInfo> stationQueryWrapper = new LambdaQueryWrapper();
        stationQueryWrapper
                .eq(TrainStationInfo::getTrainNo, trainNo)
                .eq(TrainStationInfo::getTrainDate, trainDateInt)
                .orderBy(true, true, TrainStationInfo::getTotalNum);

        List<TrainStationInfo> trainStationInfos = trainStationInfoMapper.selectList(stationQueryWrapper);
        return trainStationInfos;
    }
}
