package org.hnxxxy.rg1b.service.concern.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vdurmont.emoji.EmojiParser;
import org.hnxxxy.rg1b.common.utils.SecurityUtils;
import org.hnxxxy.rg1b.common.utils.StringUtils;
import org.hnxxxy.rg1b.domain.ConcernList;
import org.hnxxxy.rg1b.domain.PrivateMessage;
import org.hnxxxy.rg1b.domain.vo.ConcernUserVo;
import org.hnxxxy.rg1b.domain.vo.MessageWebSocketVo;
import org.hnxxxy.rg1b.mapper.ConcernListMapper;
import org.hnxxxy.rg1b.mapper.PrivateMessageMapper;
import org.hnxxxy.rg1b.service.concern.IPrivateMessageService;
import org.hnxxxy.rg1b.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class PrivateMessageServiceImpl implements IPrivateMessageService {

    @Autowired
    private PrivateMessageMapper privateMessageMapper;
    @Autowired
    private ConcernListMapper concernListMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<MessageWebSocketVo> selectHistoryMessage(Long senderUserId, Long receiverUserId) {

        List<MessageWebSocketVo> historyMessagesVo = new ArrayList<>();

        LambdaQueryWrapper<PrivateMessage> sendQueryWrapper = new LambdaQueryWrapper<>();
        sendQueryWrapper
                .eq(PrivateMessage::getOverdue, 0)
                .eq(PrivateMessage::getSenderUserId, senderUserId)
                .eq(PrivateMessage::getReceiverUserId, receiverUserId)
                .orderByAsc(PrivateMessage::getDate);
        List<PrivateMessage> sendMessages = privateMessageMapper.selectList(sendQueryWrapper);

        LambdaQueryWrapper<PrivateMessage> receiveQueryWrapper = new LambdaQueryWrapper<>();
        receiveQueryWrapper
                .eq(PrivateMessage::getOverdue, 0)
                .eq(PrivateMessage::getSenderUserId, receiverUserId)
                .eq(PrivateMessage::getReceiverUserId, senderUserId)
                .orderByAsc(PrivateMessage::getDate);
        List<PrivateMessage> receiveMessages = privateMessageMapper.selectList(receiveQueryWrapper);

        while (sendMessages.size() != 0 || receiveMessages.size() != 0){
            if (sendMessages.size() != 0 && receiveMessages.size() != 0){
                if (sendMessages.get(0).getDate().compareTo(receiveMessages.get(0).getDate()) == 1){
                    addHistoryMessagesVo(receiveMessages.get(0), historyMessagesVo, senderUserId);
                    receiveMessages.remove(0);
                }else {
                    addHistoryMessagesVo(sendMessages.get(0), historyMessagesVo, senderUserId);
                    sendMessages.remove(0);
                }
            }else if(sendMessages.size() == 0){
                addHistoryMessagesVo(receiveMessages.get(0), historyMessagesVo, senderUserId);
                receiveMessages.remove(0);
            }else {
                addHistoryMessagesVo(sendMessages.get(0), historyMessagesVo, senderUserId);
                sendMessages.remove(0);
            }
        }

        return historyMessagesVo;
    }
    private void addHistoryMessagesVo(PrivateMessage message, List<MessageWebSocketVo> historyMessagesVo, Long senderUserId){
        MessageWebSocketVo messageWebSocketVo = new MessageWebSocketVo();
        messageWebSocketVo.setMessageId(message.getMessageId());
        messageWebSocketVo.setContentType(message.getContentType());
        //处理其中所有的字符
        if (MessageWebSocketVo.STRING == (message.getContentType())){
            messageWebSocketVo.setContent(EmojiParser.parseToUnicode(new String(message.getContent())));
        }else {
            messageWebSocketVo.setContent(new String(message.getContent()));
        }
        messageWebSocketVo.setDate(message.getDate());
        if (message.getSenderUserId().equals(senderUserId)){
            messageWebSocketVo.setIsLocalUser(1);
        }else {
            messageWebSocketVo.setIsLocalUser(0);
        }
        historyMessagesVo.add(messageWebSocketVo);
    }

    @Override
    public String sendMessage(Long senderUserId, Long receiverUserId, MessageWebSocketVo message) {

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageId(UUID.randomUUID().toString());
        privateMessage.setSenderUserId(senderUserId);
        privateMessage.setReceiverUserId(receiverUserId);
        privateMessage.setDate(message.getDate());
        //如果是字符串经过处理后入库
        if (message.getContentType().equals(MessageWebSocketVo.STRING)){
            privateMessage.setContent(EmojiParser.parseToAliases(message.getContent()).getBytes());
        }else {
            //其他的类型直接入库
            privateMessage.setContent(message.getContent().getBytes());
        }
        privateMessage.setContentType(message.getContentType());
        privateMessage.setOverdue(0);

        privateMessageMapper.insert(privateMessage);

        return privateMessage.getMessageId();
    }

    @Override
    public boolean retractMessage(Long senderUserId, Long receiverUserId, String messageId) {

        LambdaQueryWrapper<PrivateMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PrivateMessage::getMessageId, messageId);

        PrivateMessage privateMessage = privateMessageMapper.selectOne(queryWrapper);
        privateMessage.setOverdue(1);

        int update = privateMessageMapper.update(privateMessage, queryWrapper);

        return update != 0 ? true : false;
    }

    @Override
    public List<ConcernUserVo> selectMessageList() {

        long userId = SecurityUtils.getUserId();

        QueryWrapper<PrivateMessage> senderQueryWrapper = new QueryWrapper<>();
        senderQueryWrapper
                .select("DISTINCT receiver_user_id")
                .eq("sender_user_id", userId)
                .eq("sender_deleted", 0)
                .eq("overdue", 0);
        List<PrivateMessage> senderPrivateMessages = privateMessageMapper.selectList(senderQueryWrapper);

        QueryWrapper<PrivateMessage> receiverQueryWrapper = new QueryWrapper<>();
        receiverQueryWrapper
                .select("DISTINCT sender_user_id")
                .eq("receiver_user_id", userId)
                .eq("receiver_deleted", 0)
                .eq("overdue", 0);
        List<PrivateMessage> receiverPrivateMessages = privateMessageMapper.selectList(receiverQueryWrapper);

        Map<String, ConcernUserVo> concernUserVoMap = new HashMap();
        List<ConcernUserVo> concernUserVos = new ArrayList<>();

        //查询发送方为当前用户的记录
        senderPrivateMessages.stream().forEach((sender) -> {
            ConcernUserVo user = new ConcernUserVo(sysUserMapper.selectUserById(sender.getReceiverUserId()));
            user.setDate(sender.getDate());
            if (!concernUserVoMap.containsKey(Long.toString(user.getUserId()))) {
                concernUserVoMap.put(Long.toString(user.getUserId()), user);
                concernUserVos.add(user);
            }
        });

        //查询接收方为当前用户的记录
        receiverPrivateMessages.stream().forEach((sender) -> {
            ConcernUserVo user = new ConcernUserVo(sysUserMapper.selectUserById(sender.getSenderUserId()));
            user.setDate(sender.getDate());
            if (!concernUserVoMap.containsKey(Long.toString(user.getUserId()))) {
                concernUserVoMap.put(Long.toString(user.getUserId()), user);
                concernUserVos.add(user);
            }else {
                user = concernUserVoMap.get(Long.toString(user.getUserId()));
            }
            LambdaQueryWrapper<ConcernList> concernListQueryWrapper = new LambdaQueryWrapper<>();
            concernListQueryWrapper.eq(ConcernList::getUserId, userId).eq(ConcernList::getConcernUserId, user.getUserId());

            ConcernList concernUser = concernListMapper.selectOne(concernListQueryWrapper);
            user.setDate(new Date());

            if (!ObjectUtils.isEmpty(concernUser) && !StringUtils.isEmpty(concernUser.getConcernId())) {
                user.setConcernId(concernUser.getConcernId());
                user.setDate(concernUser.getDate());
            }
        });

        return concernUserVos;
    }

    @Override
    public boolean deleteSelfMessage(Long userId) {

        long localUserId = SecurityUtils.getUserId();

        UpdateWrapper<PrivateMessage> senderUpdateWrapper = new UpdateWrapper<>();
        senderUpdateWrapper
                .set("sender_deleted", 1)
                .eq("sender_user_id", localUserId)
                .eq("receiver_user_id", userId);

        UpdateWrapper<PrivateMessage> receiverUpdateWrapper = new UpdateWrapper<>();
        receiverUpdateWrapper
                .set("receiver_deleted", 1)
                .eq("sender_user_id", userId)
                .eq("receiver_user_id", localUserId);

        int senderUpdated = privateMessageMapper.update(null, senderUpdateWrapper);
        int receiverUpdated = privateMessageMapper.update(null, receiverUpdateWrapper);

        return true;
    }
}
