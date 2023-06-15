package org.hnxxxy.rg1b.controller.train;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.TrainStationInfo;
import org.hnxxxy.rg1b.service.train.ITrainStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车次信息详情
 */
@RestController
@RequestMapping("/train/station")
public class TrainStationController {

    @Autowired
    private ITrainStationService trainStationService;

    /**
     * 根据车次编号和日期获取该车次站点详细信息
     * @param stationTrainCode 车次编号* G480
     * @param trainDate 日期* 20230317
     */
    @GetMapping("/list")
    public ResultVo getTrainStationList(@RequestParam String stationTrainCode,@RequestParam String trainDate){
        trainDate = trainDate.replace("-","");
        List<TrainStationInfo> trainStationInfos = trainStationService.selectTrainStationList(stationTrainCode, trainDate);
        if (trainStationInfos.isEmpty()){
            return ResultVo.error("找不到该站点详细信息");
        }else {
            return ResultVo.success(trainStationInfos);
        }
    }
}
