package org.hnxxxy.rg1b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.AttractionsRecommend;

import java.util.List;

@Mapper
public interface AttractionsRecommendMapper {
    //获取根据城市的english_name获取推荐的景点信息
    List<AttractionsRecommend> getCityRecommendPlace(@Param("englishName") String englishName);
}
