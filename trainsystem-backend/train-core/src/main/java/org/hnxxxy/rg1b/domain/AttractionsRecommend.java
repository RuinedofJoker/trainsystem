package org.hnxxxy.rg1b.domain;

import lombok.Data;

@Data
public class AttractionsRecommend {
    private Integer recommendId;
    private String attractionsName;
    private double appraiseScore;
    private Integer comments;
    private String summary;
    private Integer attractionsId;
    private Integer cityId;
    private String englishName;
}
