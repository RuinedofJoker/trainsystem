package org.hnxxxy.rg1b.service.train;

import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.domain.vo.TrainTripsDetailVo;
import org.hnxxxy.rg1b.domain.vo.TrainTripsPrice;
import org.hnxxxy.rg1b.domain.vo.TrainTripsVo;

import java.util.List;

public interface ITrainTripService {

    PageVo<TrainTripsDetailVo> selectOneWayTrips(TrainTripsVo trainTripsVo);
    PageVo<TrainTripsDetailVo> selectRoundTripTrips(TrainTripsVo trainTripsVo);
    TrainTripsPrice selectTripPrice(String stationTrainCode, String trainDate, String fromStationName, String toStationName, String tripType);
}
