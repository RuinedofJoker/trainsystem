package org.hnxxxy.rg1b.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hnxxxy.rg1b.common.constant.HttpStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketResponse<D> implements Serializable {

    private static final Integer SUCCESS = HttpStatus.SUCCESS;
    private static final Integer ERROR = HttpStatus.ERROR;

    private Integer code;
    private String msg;
    private D data;

    public static WebSocketResponse error(String msg){
        WebSocketResponse response = new WebSocketResponse();
        response.setCode(ERROR);
        response.setMsg(msg);
        return response;
    }

    public static WebSocketResponse success(String msg){
        WebSocketResponse response = new WebSocketResponse();
        response.setCode(SUCCESS);
        response.setMsg(msg);
        return response;
    }

    public static <D> WebSocketResponse<D> success(Integer code, D data){
        WebSocketResponse<D> response = new WebSocketResponse();
        response.code = SUCCESS;
        response.data = data;
        return response;
    }
}
