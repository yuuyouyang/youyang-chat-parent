package com.nf.yy.controller;

import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.impl.UserInfoServiceImpl;
import com.nf.yy.util.FileUtils;
import com.nf.yy.vo.ChatMessageVO;
import com.nf.yy.vo.ResponseVO;
import com.nf.yy.vo.SystemMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * @author smile
 */
@Component
public class MessageController {

    @Autowired
    protected UserInfoServiceImpl userInfoService;
    @Autowired
    protected SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 进行文件的撤回操作
     */
    public ResponseVO getResponseVO(String userId, boolean objectType, int count, ChatMessageVO chatMessageVO) {
        if (count > 0) {
            UserInfo userInfo = userInfoService.findByUserId(userId);
            SystemMessageVO systemMessageVO = messageWithdrawalEncapsulation(chatMessageVO, userInfo);
            // 推送到/objectId/systemMessage
            simpMessageSendingOperations.convertAndSendToUser(chatMessageVO.getUserId(), "/system/message", systemMessageVO);
            if (objectType) {
                systemMessageVO.setObjectId(chatMessageVO.getUserId());
                simpMessageSendingOperations.convertAndSendToUser(chatMessageVO.getObjectId(), "/system/message", systemMessageVO);
            }
            return ResponseVO.success("消息已成功撤回！");
        } else {
            return ResponseVO.failed("消息撤回失败！");
        }
    }

    /**
     * 封装一个消息撤回的方法
     */
    private SystemMessageVO messageWithdrawalEncapsulation(ChatMessageVO chatMessageVO, UserInfo userInfo) {
        // 若消息类型非文本，则将文件从本地移除
        if (!chatMessageVO.getMessageType().contains("text")) {
            FileUtils.deleteFile(chatMessageVO.getMessageContent());
        }
        return recallEncapsulation(chatMessageVO, userInfo);
    }

    /**
     * 封装一个SystemMessage
     */
    private SystemMessageVO recallEncapsulation(ChatMessageVO chatMessageVO, UserInfo userInfo) {
        SystemMessageVO systemMessageVO = new SystemMessageVO();
        // 指定消息类型为群消息撤回
        systemMessageVO.setMessageType(SystemMessageVO.MESSAGE_RECALL);
        systemMessageVO.setMessageUuid(chatMessageVO.getMessageUuid());
        systemMessageVO.setRequestNick(userInfo.getUserNick());
        systemMessageVO.setResponseNick(chatMessageVO.getUserNick());
        systemMessageVO.setObjectId(chatMessageVO.getObjectId());
        return systemMessageVO;
    }

}
