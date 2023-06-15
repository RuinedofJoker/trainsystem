package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hnxxxy.rg1b.domain.City;

import java.util.List;

@Mapper
public interface CityMapper extends BaseMapper<City> {

    List<City> selectCityNameByFirstStr(@Param("firstStr") String firstStr, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    List<City> selectCityFuzzyMatch(String fuzzy);

    List<City> selectHotCity();

    String getCityIdByEnglishName(@Param("englishName") String englishName);

    String getCityIdByAttractionsId(@Param("attractionsId") Integer attractionsId);

    String getEnglishNameByCityId(@Param("cityId")Integer cityId);
}
