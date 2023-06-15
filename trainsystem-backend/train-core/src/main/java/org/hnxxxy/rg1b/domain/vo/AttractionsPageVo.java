package org.hnxxxy.rg1b.domain.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.hnxxxy.rg1b.domain.dto.AttractionsPage;


@Data
public class AttractionsPageVo {
    private String englishName;
    private Integer cityId;
    private PageInfo<AttractionsPage> pageInfo;
}
