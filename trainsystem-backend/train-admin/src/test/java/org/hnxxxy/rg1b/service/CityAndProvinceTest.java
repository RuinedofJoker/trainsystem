package org.hnxxxy.rg1b.service;

import org.hnxxxy.rg1b.service.city.ICityService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CityAndProvinceTest {

    @Autowired
    ICityService cityService;

    @Test
    public void test(){
        System.out.println(cityService.selectHotCity());
    }

}
