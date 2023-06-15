package org.hnxxxy.rg1b.utils.ticketutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AllTicket {
    private String date;//日期
    private String fromStation;//起始站
    private String toStation;//终点站
    private String stationTrainCode;//车次
    private String totalNum;//站点数
    private String trainNo;//编号
}
