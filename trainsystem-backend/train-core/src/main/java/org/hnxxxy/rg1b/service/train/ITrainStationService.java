package org.hnxxxy.rg1b.service.train;

import org.hnxxxy.rg1b.domain.TrainStationInfo;

import java.util.List;

public interface ITrainStationService {
    List<TrainStationInfo> selectTrainStationList(String stationTrainCode,String trainDate);

}
