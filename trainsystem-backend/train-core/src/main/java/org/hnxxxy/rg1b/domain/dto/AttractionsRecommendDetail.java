package org.hnxxxy.rg1b.domain.dto;

import lombok.Data;
import org.hnxxxy.rg1b.domain.AttractionsRecommend;
import org.hnxxxy.rg1b.domain.Picture;

import java.util.List;

@Data
public class AttractionsRecommendDetail {
    private String attractionsName;
    private double appraiseScore;
    private Integer comments;
    private String summary;
    private Integer attractionsId;
    private String fileId;
}
