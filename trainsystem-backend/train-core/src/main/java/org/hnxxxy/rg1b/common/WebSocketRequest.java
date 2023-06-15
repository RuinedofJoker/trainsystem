package org.hnxxxy.rg1b.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketRequest<D> implements Serializable {

    public static final int RETRACT_MESSAGE = 0;
    public static final int SEND_MESSAGE = 1;
    public static final int SEND_MESSAGE_ALL = 2;

    private Integer code;
    private Long senderUserId;
    private Long receiverUserId;
    private D data;
}
