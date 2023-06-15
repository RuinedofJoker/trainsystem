package org.hnxxxy.rg1b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.Tags;

@Mapper
public interface TagsMapper {
    Tags getTagsByTagsId(@Param("tagsId")Integer tagsId);
    String getTagsByAttractionsId(@Param("attractionsId") Integer attractionsId);
}
