package org.hnxxxy.rg1b.domain;

import lombok.Data;

@Data
public class Picture {
    private String pictureId;
    private String fileId;
    private String ownedId;
    private Integer pictureType;
    private Integer isCover;
}
