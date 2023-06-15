package org.hnxxxy.rg1b.service.attractions;

import org.hnxxxy.rg1b.domain.vo.AttractionsPageVo;

public interface AttractionsPageService {
    //分页获取此城市所有景点
    AttractionsPageVo getAttractionsPageVoByEnglishNameAndPage(String englishName, Integer page);
}
