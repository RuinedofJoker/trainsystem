package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;
import org.hnxxxy.rg1b.domain.Essay;

import java.io.Serializable;

@Data
public class EssayVo extends Essay implements Serializable {
    private String content;
}
