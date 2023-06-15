package org.hnxxxy.rg1b.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainCity implements Serializable {
    private String cityNo;
    private String cityName;
    private Integer cityId;
}
