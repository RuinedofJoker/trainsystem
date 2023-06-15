package org.hnxxxy.rg1b.domain.dto;

import lombok.Data;
import org.hnxxxy.rg1b.domain.Picture;

@Data
public class AttractionsPage {
    private Integer attractionsId;
    private String attractionsName;
    private Integer cityId;
    private Double heat;
    private Double appraiseScore;
    private String address;
    //图片的fileId
    private String fileId;
}
