package org.hnxxxy.rg1b.domain.dto;

import lombok.Data;
import org.hnxxxy.rg1b.domain.TrainTrips;

import java.util.List;

@Data
public class TrainTripsPage extends TrainTrips {
    List<String> trainType;//车次类型
    String departureTimeBegin;//发车时间
    String departureTimeEnd;//发车时间
    int pageNo;
    int pageSize;
}
