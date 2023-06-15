package org.hnxxxy.rg1b.service.train.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.hnxxxy.rg1b.domain.TrainCity;
import org.hnxxxy.rg1b.mapper.TrainCityMapper;
import org.hnxxxy.rg1b.service.train.ITrainCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainCityServiceImpl implements ITrainCityService {

    @Autowired
    private TrainCityMapper trainCityMapper;

    @Override
    public int insertTrainCity(TrainCity trainCity) {
        return trainCityMapper.insertTrainCity(trainCity);
    }

    @Override
    public TrainCity selectNoByName(String cityName) {
        return trainCityMapper.selectNoByName(cityName);
    }

    @Override
    public TrainCity selectNameByNo(String cityNo) {
        return trainCityMapper.selectNameByNo(cityNo);
    }

}
