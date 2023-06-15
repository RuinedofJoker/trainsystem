package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.hnxxxy.rg1b.domain.TrainCity;

@Mapper
public interface TrainCityMapper extends BaseMapper<TrainCity> {

    int insertTrainCity(TrainCity trainCity);

    TrainCity selectNoByName(String cityName);

    TrainCity selectNameByNo(String cityNo);
}
