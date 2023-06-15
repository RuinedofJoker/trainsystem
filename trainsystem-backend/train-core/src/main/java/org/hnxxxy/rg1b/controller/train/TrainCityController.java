package org.hnxxxy.rg1b.controller.train;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.TrainCity;
import org.hnxxxy.rg1b.service.train.ITrainCityService;
import org.hnxxxy.rg1b.utils.ChineseCharacterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 城市/站点
 */
@RestController
@RequestMapping("/train/city")
public class TrainCityController {

    @Autowired
    private ITrainCityService trainCityService;

    /**
     * 根据cityNo和cityName的其中一个查另一个
     * @param cityNo 城市id(二者必须传一个) BJP
     * @param cityName 城市名 北京
     */
    @GetMapping("/info")
    public ResultVo getCityInfo(@RequestParam String cityNo,@RequestParam String cityName){
        TrainCity trainCity;
        if(cityNo!=null&&!cityNo.equals("null")&&!cityNo.equals("")){
            trainCity=trainCityService.selectNameByNo(cityNo);
            if(trainCity!=null){
                return ResultVo.success(trainCityService.selectNameByNo(cityNo));
            }
        }
        if(cityName!=null&&!cityName.equals("null")&&!cityName.equals("")){
            trainCity=trainCityService.selectNoByName(cityName);
            if (trainCity!=null){
                return ResultVo.success(trainCityService.selectNoByName(cityName));
            }
        }
            return ResultVo.error("查询失败！请正确输入城市名或城市编号！");
    }

}
