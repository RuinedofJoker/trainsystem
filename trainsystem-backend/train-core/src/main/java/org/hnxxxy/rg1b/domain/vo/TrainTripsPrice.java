package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;
import org.hnxxxy.rg1b.domain.TrainTrips;

@Data
public class TrainTripsPrice extends TrainTrips {

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
