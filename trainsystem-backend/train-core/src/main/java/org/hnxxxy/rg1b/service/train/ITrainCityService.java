package org.hnxxxy.rg1b.service.train;

import org.hnxxxy.rg1b.domain.TrainCity;

import java.util.List;

public interface ITrainCityService {

    int insertTrainCity(TrainCity trainCity);

    TrainCity selectNoByName(String cityName);

    TrainCity selectNameByNo(String cityNo);
}
