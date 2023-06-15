package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ConcernList {
    private String concernId;
    private Long userId;
    private Long concernUserId;
    private Date date;
}
