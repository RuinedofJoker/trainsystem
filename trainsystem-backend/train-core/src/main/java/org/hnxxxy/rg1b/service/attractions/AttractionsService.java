package org.hnxxxy.rg1b.service.attractions;

import org.hnxxxy.rg1b.domain.dto.AttractionsCityNameAndUrl;
import org.hnxxxy.rg1b.domain.vo.AttractionsDetailVo;
import org.hnxxxy.rg1b.domain.vo.AttractionsPageVo;

import java.util.List;

public interface AttractionsService {
    //获取景点详细信息
    AttractionsDetailVo getAttractionsDetail(Integer attractionsId);
    //获取省份和城市以及url
    List<AttractionsCityNameAndUrl> getCityNameAndUrl();
}
