package org.hnxxxy.rg1b.controller.attractions;

import org.hnxxxy.rg1b.common.ResultVo;
import org.hnxxxy.rg1b.domain.vo.AttractionsPageVo;
import org.hnxxxy.rg1b.service.attractions.AttractionsPageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/attractions")
public class AttractionsPageController {

    @Resource
    private AttractionsPageService attractionsPageService;

    //获取分页获取城市景点信息
    @GetMapping("/sight/{englishName}/{page}")
    public ResultVo getAttractionsPageVoByEnglishNameAndPage(@PathVariable String englishName, @PathVariable String page){
        AttractionsPageVo attractionsPageVo = attractionsPageService.getAttractionsPageVoByEnglishNameAndPage(englishName,Integer.parseInt(page));
        return ResultVo.success(attractionsPageVo);
    }
}
