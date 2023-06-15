package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrainStationInfo implements Serializable {

    private String stationTrainId;
    private String trainNo;
    private String cityName;
    private Integer totalNum;
    private Date arriveTime;
    private Date stayTime;
    private Integer trainDate;//车次日期
}
