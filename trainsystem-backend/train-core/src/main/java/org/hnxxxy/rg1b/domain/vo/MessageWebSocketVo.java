package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageWebSocketVo implements Serializable {

    public static final int STRING = 0;
    public static final int SMALL_PICTURE = 1;
    public static final int SMALL_FILE = 2;
    public static final int BIG_PICTURE = 3;
    public static final int BIG_FILE = 4;

    private String messageId;
    private String content;
    private Integer contentType;
    private Date date;
    private Integer isLocalUser;
}
