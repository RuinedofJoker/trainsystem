package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.util.Date;

/**
 * 最优路径算法得到的某条优化路径中的一站的信息
 */
@Data
public class RoutePath {

    private String pathId;
    private Integer trainDate;
    private String stationTrainId;//车次详细信息表中引用的id
    private String fromCityName;//上车站点
    private Date fromTime;//上车时间
    private String toCityName;//下车站点
    private Date toTime;//下车时间
    private Integer orderNum;//在当前paths中为第几车次
    private String routeId;
}
