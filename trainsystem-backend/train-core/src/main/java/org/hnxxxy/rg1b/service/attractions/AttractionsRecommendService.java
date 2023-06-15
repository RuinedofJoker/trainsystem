package org.hnxxxy.rg1b.service.attractions;

import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.vo.AttractionsRecommendDetailVo;
import org.springframework.stereotype.Service;

@Service
public interface AttractionsRecommendService {
    //获取城市推荐的景点
    AttractionsRecommendDetailVo getCityRecommendPlaceAndOther(@Param("englishName") String englishName);
}
