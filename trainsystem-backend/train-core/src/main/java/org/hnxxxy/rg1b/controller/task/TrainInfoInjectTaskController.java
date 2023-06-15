package org.hnxxxy.rg1b.controller.task;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.TrainInfoRecord;
import org.hnxxxy.rg1b.task.TrainInfoInjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train/admin/task")
public class TrainInfoInjectTaskController {

    @Autowired
    TrainInfoInjectTask trainInfoInjectTask;

    @PutMapping("/changeType")
    @PreAuthorize("@ss.hasRole('admin')")
    public ResultVo setFetchTrainType(@RequestBody String trainType){
        trainInfoInjectTask.setFetchTrainType(trainType);
        return ResultVo.success("修改成功");
    }

    @GetMapping("/getType")
    @PreAuthorize("@ss.hasRole('admin')")
    public ResultVo getFetchTrainType(){
        return ResultVo.success(trainInfoInjectTask.getFetchTrainType());
    }

    @DeleteMapping("/clean")
    @PreAuthorize("@ss.hasRole('admin')")
    public ResultVo cleanOverdueTrainInfo(){
        trainInfoInjectTask.deleteTrips();
        return ResultVo.success("删除成功");
    }

    @PostMapping("/putInfoByDay")
    @PreAuthorize("@ss.hasRole('admin')")
    public ResultVo putTrainTripsInfoByDesignateDate(String designateDate){
        String taskStatu = trainInfoInjectTask.insertTripsByDesignateDate(designateDate, true);
        return ResultVo.success(taskStatu);
    }

    @PostMapping("/putInfoUntilDay")
    @PreAuthorize("@ss.hasRole('admin')")
    public ResultVo putTrainTripsInfoUntilDesignateDate(String designateDate){
        String taskStatu = trainInfoInjectTask.insertTripsUntilDesignateDay(designateDate, true);
        return ResultVo.success(taskStatu);
    }

    @GetMapping("/getRecord")
    @PreAuthorize("@ss.hasRole('admin')")
    public ResultVo getTrainInfoRecord(){
        List<TrainInfoRecord> trainInfoRecords = trainInfoInjectTask.getTrainInfoRecord();
        return ResultVo.success(trainInfoRecords);
    }
}
