package org.hnxxxy.rg1b.service.province.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.domain.City;
import org.hnxxxy.rg1b.domain.Province;
import org.hnxxxy.rg1b.mapper.CityMapper;
import org.hnxxxy.rg1b.mapper.ProvinceMapper;
import org.hnxxxy.rg1b.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements IProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<Province> selectAllProvince() {
        return provinceMapper.selectList(null);
    }

    @Override
    public List<City> selectAllCityByProvinceName(String provinceName) {
        LambdaQueryWrapper<Province> provinceQueryWrapper = new LambdaQueryWrapper<>();
        provinceQueryWrapper.like(Province::getProvinceName, "%" + provinceName + "%");
        List<Province> provinces = provinceMapper.selectList(provinceQueryWrapper);
        if (provinces.isEmpty()) {
            return null;
        }

        LambdaQueryWrapper<City> cityQueryWrapper = new LambdaQueryWrapper<>();
        cityQueryWrapper.eq(City::getProvinceId, provinces.get(0).getProvinceId());
        List<City> cities = cityMapper.selectList(cityQueryWrapper);

        return cities;
    }
}

