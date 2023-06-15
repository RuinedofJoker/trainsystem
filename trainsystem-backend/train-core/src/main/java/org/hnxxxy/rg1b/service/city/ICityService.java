package org.hnxxxy.rg1b.service.city;

import org.hnxxxy.rg1b.domain.City;

import java.util.List;

public interface ICityService {
    List<City> selectCityNameByFirstStr(String firstStr, int pageNo, int pageSize);

    List<City> selectCityFuzzyMatch(String fuzzyCity);

    List<City> selectHotCity();
}
