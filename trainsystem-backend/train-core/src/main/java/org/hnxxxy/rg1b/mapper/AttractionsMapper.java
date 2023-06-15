package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.Attractions;
import org.hnxxxy.rg1b.domain.dto.AttractionsCityNameAndUrl;

import java.util.List;

@Mapper
public interface AttractionsMapper extends BaseMapper<Attractions> {
    List<AttractionsCityNameAndUrl> getCityNameAndUrl();
    //通过id获取景点信息
    Attractions getAttractionsByAttractionsId(@Param("attractionsId") Integer attractionsId);
}
