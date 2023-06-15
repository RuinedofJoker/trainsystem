package org.hnxxxy.rg1b.controller.attractions;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.dto.AttractionsCityNameAndUrl;
import org.hnxxxy.rg1b.domain.vo.AttractionsDetailVo;
import org.hnxxxy.rg1b.service.attractions.AttractionsService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/attractions")
public class AttractionsController {

    @Resource
    private AttractionsService attractionsService;

    //获取景点详细信息
    @GetMapping("/detail/{attractionsId}")
    public ResultVo getAttractionsDetail(@PathVariable Integer attractionsId){
        AttractionsDetailVo detail = attractionsService.getAttractionsDetail(attractionsId);
        if(ObjectUtils.isEmpty(detail)){
            return ResultVo.error("获取不到该景点信息");
        }else {
            return ResultVo.success(detail);
        }
    }

    //获取省份和城市的名称和url
    @GetMapping("/city")
    public ResultVo getCityNameAndUrl(){
        List<AttractionsCityNameAndUrl> cityNameAndUrlList = attractionsService.getCityNameAndUrl();
        return ResultVo.success(cityNameAndUrlList);
    }
}
