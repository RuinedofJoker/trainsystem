package org.hnxxxy.rg1b.domain;

import lombok.Data;

@Data
public class Attractions {
    private Integer attractionsId;
    private String attractionsName;
    private Integer cityId;
    private Long userId;
    private Double price;
    private Double heat;
    private Double appraiseScore;
    private String address;
    private String openingHours;
    private String phoneNumber;
    private String fileId;
}
