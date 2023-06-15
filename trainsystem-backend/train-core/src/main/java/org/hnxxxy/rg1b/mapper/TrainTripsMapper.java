package org.hnxxxy.rg1b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hnxxxy.rg1b.domain.TrainTrips;
import org.hnxxxy.rg1b.domain.dto.TrainTripsPage;

import java.util.List;

@Mapper
public interface TrainTripsMapper extends BaseMapper<TrainTrips> {

    @Insert("insert into train_trips(train_no,station_train_code,train_date,from_station_name,to_station_name,total_num,seat_types) values (#{trainNo},#{stationTrainCode},#{trainDate},#{fromStationName},#{toStationName},#{totalNum},#{seatTypes})")
    int insertTrainTripsInfo(TrainTrips trainTrips);

    int deleteDelayTrainTripsInfo(int trainDate);

    List<TrainTrips> selectOneWayPage(TrainTripsPage trainTripsPage);
    int selectOneWayPageCount(TrainTripsPage trainTripsPage);

    int selectAllCount();

    TrainTrips selectOneByCurrCount(int currCount);

    int updateSeatTypes(TrainTrips trainTrips);

    int updatePrice(TrainTrips trainTrips);
}
