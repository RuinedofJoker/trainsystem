package org.hnxxxy.rg1b.controller.train;

import org.hnxxxy.rg1b.common.PageVo;
import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.domain.TrainTrips;
import org.hnxxxy.rg1b.domain.dto.Ticket;
import org.hnxxxy.rg1b.domain.vo.TrainTripsDetailVo;
import org.hnxxxy.rg1b.domain.vo.TrainTripsPrice;
import org.hnxxxy.rg1b.domain.vo.TrainTripsVo;
import org.hnxxxy.rg1b.service.train.ITrainTripService;
import org.hnxxxy.rg1b.utils.ticketutils.PurposeCode;
import org.hnxxxy.rg1b.utils.ticketutils.TicketUtilsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车次信息
 */
@RestController
@RequestMapping("/train/trips")
public class TrainTripController {

    @Autowired
    private ITrainTripService trainTripService;

    @PostMapping("/list")
    public ResultVo getTrainTripList(@RequestBody TrainTripsVo trainTripsVo){

        PageVo<TrainTripsDetailVo> resultList;
        if (StringUtils.isEmpty(trainTripsVo.getToDate())){
            resultList = trainTripService.selectOneWayTrips(trainTripsVo);
        }else {
            resultList = trainTripService.selectRoundTripTrips(trainTripsVo);
        }
        return ResultVo.success(resultList);
    }

    @GetMapping("/price")
    public ResultVo getTrainTripPrice(@RequestParam String stationTrainCode, @RequestParam String trainDate, @RequestParam String fromStationName, @RequestParam String toStationName, @RequestParam String tripType){
        TrainTripsPrice tripsPrice = trainTripService.selectTripPrice(stationTrainCode, trainDate, fromStationName, toStationName, tripType);
        return ResultVo.success(tripsPrice);
    }
}
