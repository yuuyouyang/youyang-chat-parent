package com.nf.yy.controller;

import com.nf.yy.entity.*;
import com.nf.yy.service.UserMessageService;
import com.nf.yy.service.impl.MessageHandlingServiceImpl;
import com.nf.yy.service.impl.UserFriendServiceImpl;
import com.nf.yy.service.impl.UserInfoServiceImpl;
import com.nf.yy.service.impl.UserMessageServiceImpl;
import com.nf.yy.vo.ChatMessageVO;
import com.nf.yy.vo.MessageHandlingVO;
import com.nf.yy.vo.ResponseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author smile
 */
@RestController
@RequestMapping("/api/handling")
public class MessageHandlingController extends MessageController {

    @Autowired
    private MessageHandlingServiceImpl messageHandlingService;
    @Autowired
    private UserFriendServiceImpl userFriendService;
    @Autowired
    private UserMessageServiceImpl userMessageService;

    /**
     * 查询当前用户所有需要处理的消息
     */
    @GetMapping("")
    public ResponseVO queryAll(Authentication authentication) {
        return ResponseVO.success(messageHandlingService.queryAll(authentication.getName()));
    }

    /**
     * 查询指定好友在数据库是否已有未处理的事务
     */
    @GetMapping("/{friendId}")
    public ResponseVO query(Authentication authentication, @PathVariable String friendId) {
        return ResponseVO.success(messageHandlingService.query(authentication.getName(), friendId));
    }

    /**
     * 新增事件处理消息
     */
    @PostMapping("")
    public ResponseVO insert(Authentication authentication, MessageHandling messageHandling) {
        messageHandling.setUserId(authentication.getName());
        return ResponseVO.success(messageHandlingService.insert(messageHandling));
    }

    /**
     * 修改事务状态与结果
     */
    @PutMapping("")
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO update(@RequestBody MessageHandling messageHandling) {
        System.out.println(messageHandling);
        if (messageHandlingService.update(messageHandling) > 0) {
            if (messageHandling.getHandlingResults() && submit(messageHandling)) {
                return ResponseVO.success("已同意并添加为好友！");
            }
        }
        return ResponseVO.failed("遇到未知错误！");

    }

    /**
     * 将好友添加到数据库
     */
    public Boolean submit(MessageHandling messageHandling) {
        UserFriend userFriend1 = new UserFriend(messageHandling.getUserId(), messageHandling.getFriendId(), "");
        UserFriend userFriend2 = new UserFriend(messageHandling.getFriendId(), messageHandling.getUserId(), "");
        if (userFriendService.insert(userFriend1) > 0 && userFriendService.insert(userFriend2) > 0) {
            encapsulation(messageHandling);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加好友,并封装一个UserMessage并推送到客户端
     */
    private void encapsulation(MessageHandling messageHandling) {
        UserInfo userInfo = userInfoService.findByUserId(messageHandling.getFriendId());
        UserMessage userMessage = new UserMessage();
        userMessage.setUserId(userInfo.getUserId());
        userMessage.setFriendId(messageHandling.getUserId());
        userMessage.setMessageUuid(UUID.randomUUID().toString());
        userMessage.setMessageTime(new Date());
        userMessage.setMessageType("text");
        userMessage.setMessageStatus(false);
        userMessage.setMessageContent("我们已经是好友了，开始聊天把！");
        userMessageService.insertMessage(userMessage);
        /* 推送到前端 */
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        BeanUtils.copyProperties(userMessage, chatMessageVO);
        chatMessageVO.setObjectId(messageHandling.getUserId());
        chatMessageVO.setObjectType(1);
        simpMessageSendingOperations.convertAndSendToUser(messageHandling.getFriendId(), "/user/message", chatMessageVO);
        chatMessageVO.setObjectId(messageHandling.getFriendId());
        simpMessageSendingOperations.convertAndSendToUser(messageHandling.getUserId(), "/user/message", chatMessageVO);
    }

}
