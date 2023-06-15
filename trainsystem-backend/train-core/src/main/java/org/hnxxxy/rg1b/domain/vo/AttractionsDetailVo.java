package org.hnxxxy.rg1b.domain.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.hnxxxy.rg1b.domain.Essay;
import org.hnxxxy.rg1b.domain.dto.AttractionsDetail;


@Data
public class AttractionsDetailVo {
    private String englishName;
    private AttractionsDetail attractionsDetail;
    private PageInfo<Essay> essay;
}
