package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Essay {
    private String essayId;
    private String essayTitle;
    private Long userId;
    private String summary;
    private String articleId;
    private String coverPictureId;
    private Integer placeType;
    private String placeId;
    private Date date;
    private Integer lookNum;
    private Integer isEffective;
    private Integer isPublished;
}
