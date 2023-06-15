package org.hnxxxy.rg1b.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVo{
    private String commentId;
    private Long userId;
    private Integer commentType;
    private String articleId;
    private Integer isMainReply;
    private String mainReplyId;
    private String replyId;
    private String content;
    private Date date;
    private Integer likeNum;
    private Integer isEffective;
}
