package org.hnxxxy.rg1b.service.city.impl;

import org.hnxxxy.rg1b.domain.City;
import org.hnxxxy.rg1b.mapper.CityMapper;
import org.hnxxxy.rg1b.service.city.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    @Cacheable(value = "cityFirstStr", key = "#firstStr+'_'+#pageNo+'_'+#pageSize")
    public List<City> selectCityNameByFirstStr(String firstStr, int pageNo, int pageSize) {
        return cityMapper.selectCityNameByFirstStr(firstStr, pageNo, pageSize);
    }

    @Override
    public List<City> selectCityFuzzyMatch(String fuzzyCity) {
        char[] cityChar = fuzzyCity.toCharArray();
        String cityStr = "%";
        for (char currChar : cityChar){
            cityStr = cityStr + currChar + "%";
        }
        List<City> cityList = cityMapper.selectCityFuzzyMatch(cityStr);
        return cityList;
    }

    @Override
    public List<City> selectHotCity() {
        return cityMapper.selectHotCity();
    }
}
