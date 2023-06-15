package org.hnxxxy.rg1b.controller.attractions;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.vo.AttractionsRecommendDetailVo;
import org.hnxxxy.rg1b.service.attractions.AttractionsRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attractions")
public class AttractionsRecommendController {
    @Autowired
    private AttractionsRecommendService attractionsRecommendService;

    @GetMapping("/place/{englishName}")
    public ResultVo getCityRecommendPlaceAndOther(@PathVariable String englishName){
        AttractionsRecommendDetailVo attractionsRecommendVo = attractionsRecommendService.getCityRecommendPlaceAndOther(englishName);
        return ResultVo.success(attractionsRecommendVo);
    }
}
