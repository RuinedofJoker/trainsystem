package org.hnxxxy.rg1b.service.province;

import org.hnxxxy.rg1b.domain.City;
import org.hnxxxy.rg1b.domain.Province;

import java.util.List;

public interface IProvinceService {

    List<Province> selectAllProvince();

    List<City> selectAllCityByProvinceName(String provinceName);

}
