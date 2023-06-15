package org.hnxxxy.rg1b.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttractionsDetail {
    private Integer attractionsId;
    private String attractionsName;
    private Double price;
    private Double heat;
    private Double appraiseScore;
    private String address;
    private String openingHours;
    private String phoneNumber;
    private String Detail;
    private String tags;
    private List<String> fileIdList;
}
