package org.hnxxxy.rg1b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.AttractionsTags;

@Mapper
public interface AttractionsTagsMapper {
    AttractionsTags getAttractionsTagsByAttractionsId(@Param("attractionsId")Integer attractionsId);
}
