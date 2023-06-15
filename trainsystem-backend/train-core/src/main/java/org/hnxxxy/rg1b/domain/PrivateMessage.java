package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PrivateMessage {
    private String messageId;
    private Long senderUserId;
    private Long receiverUserId;
    private byte[] content;
    private Integer contentType;
    private Date date;
    private Integer overdue;
    private Integer senderDeleted;
    private Integer receiverDeleted;
}
