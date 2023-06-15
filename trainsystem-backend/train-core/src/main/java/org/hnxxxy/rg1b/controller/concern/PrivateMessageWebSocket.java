package org.hnxxxy.rg1b.controller.concern;

import org.hnxxxy.rg1b.common.*;
import org.hnxxxy.rg1b.common.core.domain.model.LoginUser;
import org.hnxxxy.rg1b.common.core.redis.RedisCache;
import org.hnxxxy.rg1b.domain.vo.MessageWebSocketVo;
import org.hnxxxy.rg1b.service.concern.IPrivateMessageService;
import org.hnxxxy.rg1b.service.concern.IUserConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/concern/message", encoders = MessageWebSocketVoWebSocketEncoder.class, decoders = MessageWebSocketVoWebSocketDecoder.class, configurator= GetHttpSessionConfigurator.class)
@Component
public class PrivateMessageWebSocket {

    private static IUserConcernService userConcernService;
    private static IPrivateMessageService privateMessageService;
    private static RedisCache redisCache;

    private static ConcurrentHashMap<Long, Session> sessionPool = new ConcurrentHashMap<>();
    private HttpSession httpSession;

    @Autowired
    public void setUserConcernService(IUserConcernService userConcernService) {
        PrivateMessageWebSocket.userConcernService = userConcernService;
    }

    @Autowired
    public void setPrivateMessageService(IPrivateMessageService privateMessageService) {
        PrivateMessageWebSocket.privateMessageService = privateMessageService;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        PrivateMessageWebSocket.redisCache = redisCache;
    }

    /**
     * 用户上线
     */
    @OnOpen
    public void userOnline(Session session, EndpointConfig config){

        httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String userKey = (String) httpSession.getAttribute("userKey");
        LoginUser loginUser = redisCache.getCacheObject(userKey);
        Long userId = loginUser.getUserId();

        try {
            sessionPool.put(userId, session);
        }catch (Exception e){
            session.getAsyncRemote().sendObject(WebSocketResponse.error("用户未登录"));
        }
    }

    /**
     * 用户下线
     */
    @OnClose
    public void userOffline(Session session){

        String userKey = (String) httpSession.getAttribute("userKey");
        LoginUser loginUser = redisCache.getCacheObject(userKey);
        Long userId = loginUser.getUserId();

        try {
            sessionPool.remove(userId);
            httpSession.removeAttribute("userKey");
        }catch (Exception e){
            session.getAsyncRemote().sendObject(WebSocketResponse.error("用户未登录"));
        }
        session.getAsyncRemote().sendObject(WebSocketResponse.success("下线成功"));
    }


    @OnMessage
    public void sendOneMessage(WebSocketRequest<MessageWebSocketVo> request, Session session){

        String userKey = (String) httpSession.getAttribute("userKey");
        LoginUser loginUser = redisCache.getCacheObject(userKey);
        Long senderUserId = loginUser.getUserId();
        Long receiverUserId = request.getReceiverUserId();
        Session senderSession = null;
        Session receiverSession = null;
        boolean receiverLogin = true;
        MessageWebSocketVo message = request.getData();

        try {
            senderSession = sessionPool.get(senderUserId);
            try {
                receiverSession = sessionPool.get(receiverUserId);
            }catch (Exception e){
                receiverLogin = false;
            }
            if (!session.equals(senderSession)){
                throw new Exception();
            }
            if (ObjectUtils.isEmpty(senderSession)){
                throw new Exception();
            }
            if (ObjectUtils.isEmpty(receiverSession)){
                receiverLogin = false;
            }
        }catch (Exception e){
            session.getAsyncRemote().sendObject(WebSocketResponse.error("用户未登录"));
        }

        int code = request.getCode();

        //撤回消息
        if (code == WebSocketRequest.RETRACT_MESSAGE){
            //消息从数据库删除
            privateMessageService.retractMessage(senderUserId, receiverUserId, message.getMessageId());

            //用户在线,应该提醒客户端撤销
            if(receiverLogin){
                //websocket发送提醒撤销消息
                receiverSession.getAsyncRemote().sendObject(WebSocketResponse.success(0, message));
            }
            senderSession.getAsyncRemote().sendObject(WebSocketResponse.success("撤回成功"));
        }

        //发送消息
        if (code == WebSocketRequest.SEND_MESSAGE){
            message.setDate(new Date());
            //消息入库
            message.setMessageId(privateMessageService.sendMessage(senderUserId, receiverUserId, message));

            message.setIsLocalUser(1);
            senderSession.getAsyncRemote().sendObject(WebSocketResponse.success(200, message));
            //websocket发送消息
            if (receiverLogin){
                message.setIsLocalUser(0);
                receiverSession.getAsyncRemote().sendObject(WebSocketResponse.success(1, message));
            }
        }

        //发送到全体
        if (code == WebSocketRequest.SEND_MESSAGE_ALL){
        }
    }
}
