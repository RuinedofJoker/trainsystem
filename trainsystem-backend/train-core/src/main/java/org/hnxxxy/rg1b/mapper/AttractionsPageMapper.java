package org.hnxxxy.rg1b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.dto.AttractionsPage;

import java.util.List;

@Mapper
public interface AttractionsPageMapper {
    //通过城市id获取景点信息存储在，再通过pageInfo进行分页
    List<AttractionsPage> getAttractionsPageByEnglishName(@Param("cityId") Integer cityId);
}
