package org.hnxxxy.rg1b.common;

import com.alibaba.fastjson2.JSON;
import org.hnxxxy.rg1b.domain.vo.MessageWebSocketVo;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageWebSocketVoWebSocketEncoder implements Encoder.Text<WebSocketResponse<MessageWebSocketVo>> {
    @Override
    public String encode(WebSocketResponse<MessageWebSocketVo> resultVo){
        return JSON.toJSONString(resultVo);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
