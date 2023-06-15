package org.hnxxxy.rg1b.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket implements Serializable {
    private String stationTrainCode;//车次
    private String fromStationName;//始发站名字
    private String toStationName;//到达站名字
    private String startTime;//出发时间
    private String arriveTime;//到达时间
    private String lishi;//历时
    private Integer totalNum;//站点数
    private Integer stationNo;
    private String stationName;

    private String gjrw;//高级软卧
    private String rw;//软卧
    private String wz;//无座
    private String yw;//硬卧
    private String yz;//硬座
    private String ed;//二等座
    private String yd;//一等座
    private String td;//商务座，特等座

    private String trainNo;//编号
    private String fromStationNo;//始发站编号
    private String toStationNo;//到达站编号
    private String seatTypes;//座位类型
    private String trainDate;//车次日期

    //AI A4 软卧一等
    private Double rwPrice;
    //AJ A3 硬卧二等
    private Double ywPrice;

    //A1 O 硬座
    private Double yzPrice;
    //A6 高级软卧
    private Double gjrwPrice;
    //A9 商务座 特等座
    private Double tdPrice;

    //M 一等座
    private Double ydPrice;
    //WZ O 无座/二等座包座/硬座
    private Double wzPrice;
    private Double edPrice;
}

