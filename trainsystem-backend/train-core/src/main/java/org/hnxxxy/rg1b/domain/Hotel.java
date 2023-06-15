package org.hnxxxy.rg1b.domain;
import lombok.Data;

import java.io.Serializable;

@Data
public class Hotel implements Serializable {
    private String hotelId;//酒店ID
    private String hotelName;//酒店名称
    private Integer cityId;
    private Integer price;//酒店价格
    private Long userId;
    private String star;//酒店星级
    private String appraiseScore;//酒店评分
    private String summary;
    private String hotelPoliciesId;//酒店描述
    private String address;//酒店地址
    private String hotelPicture;
}
