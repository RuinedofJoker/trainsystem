package org.hnxxxy.rg1b.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hnxxxy.rg1b.domain.vo.MessageWebSocketVo;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageWebSocketVoWebSocketDecoder implements Decoder.Text<WebSocketRequest> {
    @Override
    public WebSocketRequest decode(String text){
        ObjectMapper mapper = new ObjectMapper();
        WebSocketRequest<MessageWebSocketVo> webSocketRequest = null;
        try {
            webSocketRequest = mapper.readValue(text, new TypeReference<WebSocketRequest<MessageWebSocketVo>>() {});
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return webSocketRequest;
    }

    @Override
    public boolean willDecode(String text) {
        return (text != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
