package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentArea implements Serializable {

    public static final Integer ATTRACTIONS = 0;
    public static final Integer ATTRACTIONS_APPRAISE = 1;
    public static final Integer HOTEL = 2;
    public static final Integer ESSAY = 3;

    private String commentId;
    private Long userId;
    private Integer commentType;
    private String articleId;
    private Integer isMainReply;
    private String mainReplyId;
    private String replyId;
    private byte[] content;
    private Date date;
    private Integer likeNum;
    private Integer isEffective;
}
