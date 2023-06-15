package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.util.Date;

@Data
public class HotelAppraise {
    private String hotelAppraiseId;
    private String hotelId;
    private Long userId;
    private String articleId;
    private Date date;
    private Integer lookNum;
    private Integer isEffective;
}
