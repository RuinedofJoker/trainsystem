package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;
import org.hnxxxy.rg1b.domain.dto.AttractionsRecommendDetail;

import java.util.List;

@Data
public class AttractionsRecommendDetailVo {
    private String englishName;
    private Integer cityId;
    private String cityPictureFileId;
    private List<AttractionsRecommendDetail> attractionsRecommendDetail;
}
