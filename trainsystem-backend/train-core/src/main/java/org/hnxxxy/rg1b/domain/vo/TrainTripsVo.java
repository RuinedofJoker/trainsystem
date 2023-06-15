package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class TrainTripsVo {
    String fromCity;
    String toCity;
    String fromDate;
    String toDate;
    List<String> trainType;//车次类型
    String departureTime;//发车时间
    Integer pageNo;
    Integer pageSize;
}
