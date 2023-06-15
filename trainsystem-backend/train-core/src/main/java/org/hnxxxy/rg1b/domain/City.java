package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {
    private Integer cityId;
    private String cityName;
    private Integer cityHotspot;
    private Integer provinceId;
    private String englishName;
}
