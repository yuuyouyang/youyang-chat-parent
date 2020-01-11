package com.nf.yy.controller;

import com.nf.yy.entity.GroupMessage;
import com.nf.yy.entity.UserMessage;
import com.nf.yy.service.impl.GroupMessageServiceImpl;
import com.nf.yy.service.impl.UserMessageServiceImpl;
import com.nf.yy.vo.ChatMessageVO;
import com.nf.yy.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author smile
 */
@RestController
public class StompController {

    /** SimpMessagingTemplate能够在应用的任何地方向前端推送消息。 */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    @Autowired
    private GroupMessageServiceImpl groupMessageService;
    @Autowired
    private UserMessageServiceImpl userMessageService;

    /**
     * @param groupMessage 客户端发送的群聊消息
     */
    @PostMapping("/api/group/message")
    public ResponseVO groupMessage(Authentication authentication, GroupMessage groupMessage) {
        groupMessage.setUserId(authentication.getName());
        String uuid = UUID.randomUUID().toString();
        groupMessage.setMessageUuid(uuid);
        groupMessage.setMessageTime(new Date());
        if (groupMessageService.insertMessage(groupMessage) > 0) {
            ChatMessageVO chatMessageVO = groupMessageService.encapsulation(groupMessage);
            simpMessageSendingOperations.convertAndSendToUser(groupMessage.getGroupId(), "/group/message", chatMessageVO);
            return ResponseVO.success("消息发送成功!message-id=" + uuid);
        } else {
            return ResponseVO.failed("遇到未知异常,消息发送失败!");
        }
    }

    /**
     * @param userMessage 客户端发送的私聊消息
     */
    @PostMapping("/api/user/message")
    public ResponseVO userMessage(Authentication authentication, UserMessage userMessage) {
        userMessage.setUserId(authentication.getName());
        String uuid = UUID.randomUUID().toString();
        userMessage.setMessageUuid(uuid);
        userMessage.setMessageTime(new Date());
        System.out.println(userMessage);
        if (userMessageService.insertMessage(userMessage) > 0) {
            ChatMessageVO chatMessageVO = userMessageService.encapsulation(userMessage);
            simpMessageSendingOperations.convertAndSendToUser(userMessage.getUserId(), "/user/message", chatMessageVO);
            simpMessageSendingOperations.convertAndSendToUser(userMessage.getFriendId(), "/user/message", chatMessageVO);
            return ResponseVO.success("消息发送成功!message-id=" + uuid);
        } else {
            return ResponseVO.failed("遇到未知异常,消息发送失败!");
        }
    }


    /**
     * @param groupMessage 客户端发送的群消息
     */
    // @MessageMapping("/group/message")
    // public void groupMessage(GroupMessage groupMessage) {
    //     System.out.println(groupMessage);
    //     groupMessage.setMessageUuid(UUID.randomUUID().toString());
    //     groupMessage.setMessageTime(new Date());
    //     groupMessageService.insertMessage(groupMessage);
    //     ChatMessageVO chatMessageVO = groupMessageService.encapsulation(groupMessage);
    //     simpMessageSendingOperations.convertAndSendToUser(groupMessage.getGroupId(), "/group/message", chatMessageVO);
    // }

}
