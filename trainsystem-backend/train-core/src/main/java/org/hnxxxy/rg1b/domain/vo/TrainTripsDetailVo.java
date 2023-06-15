package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrainTripsDetailVo implements Serializable {
    String stationTrainCode;//车次
    String fromStationName;//出发站
    String toStationName;//到达站
    String fromTime;//出发时间
    String toTime;//到达时间
    String elapseTime;//历时
    String isToday;//是否当天到达
}
