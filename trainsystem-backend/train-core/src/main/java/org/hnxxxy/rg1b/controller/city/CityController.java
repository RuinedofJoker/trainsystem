package org.hnxxxy.rg1b.controller.city;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.City;
import org.hnxxxy.rg1b.service.city.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    ICityService cityService;

    /**
     * 分页查询城市列表
     * @param firstStr 城市拼音首字母(选择了热点城市可以不传，否则必须要传)
     */
    @GetMapping("/list")
    public ResultVo getCityList(@RequestParam String firstStr, @RequestParam int pageNo, @RequestParam int pageSize){
        List<City> firstStrCityList = cityService.selectCityNameByFirstStr(firstStr, pageNo, pageSize);
        return ResultVo.success(firstStrCityList);
    }

    /**
     * 模糊查询城市
     * @param fuzzyCity 模糊的城市名
     */
    @GetMapping("/fuzzy")
    public ResultVo getCityFuzzy(@RequestParam String fuzzyCity){
        List<City> fuzzyCityList = cityService.selectCityFuzzyMatch(fuzzyCity);
        return ResultVo.success(fuzzyCityList);
    }

    /**
     * 查询热点城市
     */
    @GetMapping("/hotspot")
    public ResultVo getHotCity(){
        List<City> hotCityList = cityService.selectHotCity();
        return ResultVo.success(hotCityList);
    }
}
