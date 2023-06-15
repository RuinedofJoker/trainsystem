package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrainTrips implements Serializable {

    private String trainNo;//编号
    private String stationTrainCode;//车次
    private Integer trainDate;//车次日期
    private String fromStationName;
    private String toStationName;
    private Integer totalNum;//共有多少站
    private String seatTypes;//座位类型

    private String gjrw;//高级软卧
    private String rw;//软卧
    private String wz;//无座
    private String yw;//硬卧
    private String yz;//硬座
    private String ed;//二等座
    private String yd;//一等座
    private String td;//商务座，特等座
}
