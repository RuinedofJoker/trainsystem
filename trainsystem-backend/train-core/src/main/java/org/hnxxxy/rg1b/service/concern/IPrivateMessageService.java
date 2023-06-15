package org.hnxxxy.rg1b.service.concern;

import org.hnxxxy.rg1b.domain.PrivateMessage;
import org.hnxxxy.rg1b.domain.vo.ConcernUserVo;
import org.hnxxxy.rg1b.domain.vo.MessageWebSocketVo;

import java.util.List;

public interface IPrivateMessageService {

    /**
     * 查询用户的历史私信
     * @param senderUserId 发送者
     * @param receiverUserId 接收者
     * @return 历史私信
     */
    List<MessageWebSocketVo> selectHistoryMessage(Long senderUserId, Long receiverUserId);

    /**
     * 发送消息
     */
    String sendMessage(Long senderUserId, Long receiverUserId, MessageWebSocketVo messageContent);

    /**
     * 撤回消息
     */
    boolean retractMessage(Long senderUserId, Long receiverUserId, String messageId);

    List<ConcernUserVo> selectMessageList();

    boolean deleteSelfMessage(Long userId);
}
