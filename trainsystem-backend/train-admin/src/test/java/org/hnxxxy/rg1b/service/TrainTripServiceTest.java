package org.hnxxxy.rg1b.service;

import org.hnxxxy.rg1b.TrainApplication;
import org.hnxxxy.rg1b.service.train.ITrainTripService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TrainApplication.class})
public class TrainTripServiceTest {

    @Autowired
    ITrainTripService trainTripService;

    @Test
    public void selectTripPriceTest(){
        trainTripService.selectTripPrice("G47", "20230405", "大连北", "哈尔滨西", "");
    }
}
