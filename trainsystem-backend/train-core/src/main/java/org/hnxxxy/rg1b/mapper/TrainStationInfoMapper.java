package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.hnxxxy.rg1b.domain.TrainStationInfo;

import java.util.List;

@Mapper
public interface TrainStationInfoMapper extends BaseMapper<TrainStationInfo> {

    int insertTrainStationInfo(TrainStationInfo trainStationInfo);

    List<TrainStationInfo> selectStartMap(TrainStationInfo trainStationInfo);

    List<TrainStationInfo> selectArrivedTrips(TrainStationInfo trainStationInfo);

    TrainStationInfo selectStayedTripsTime(TrainStationInfo trainStationInfo);

    List<TrainStationInfo> selectTrainNoBeforeCurrCity(TrainStationInfo trainStationInfo);

    List<TrainStationInfo> selectTripsBeforeCurrCity(TrainStationInfo trainStationInfo);
}
