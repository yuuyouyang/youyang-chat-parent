package com.nf.yy.controller;

import com.github.pagehelper.PageInfo;
import com.nf.yy.vo.ResponseVO;
import com.nf.yy.service.impl.GroupMessageServiceImpl;
import com.nf.yy.vo.ChatMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author smile
 */
@RestController
@RequestMapping("/api/group/message")
public class GroupMessageController extends MessageController {

    @Autowired
    private GroupMessageServiceImpl groupMessageService;

    /**
     * 根据房间Id获取当前群聊的历史消息（分页查询）
     */
    @GetMapping("/page")
    public ResponseVO pageQueryMessage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, String groupId) {
        List<ChatMessageVO> chatMessageVOS = groupMessageService.pageQueryMessage(pageNum, 50, groupId);
        PageInfo<ChatMessageVO> messageVOPageInfo = new PageInfo<>(chatMessageVOS, 5);
        return ResponseVO.success(messageVOPageInfo);
    }

    /**
     * 根据群id获取该群最新50条消息
     */
    @GetMapping("/lasts/{groupId}")
    public ResponseVO getLatestMessage(@PathVariable String groupId) {
        return ResponseVO.success(groupMessageService.getLatestMessage(groupId));
    }

    /**
     * 根据ID获取最新一条消息
     */
    @RequestMapping("/last/{groupId}")
    public ResponseVO getLastMessage(@PathVariable String groupId) {
        return ResponseVO.success(groupMessageService.getLastMessage(groupId));
    }

    /**
     * 将群消息从数据库移除，用于群消息撤回
     */
    @DeleteMapping("/{messageUuid}")
    public ResponseVO deleteMessage(@PathVariable String messageUuid, Authentication authentication) {
        ChatMessageVO chatMessageVO = groupMessageService.findByUuid(messageUuid);
        int count = groupMessageService.deleteMessage(messageUuid);
        return getResponseVO(authentication.getName(), count, chatMessageVO);
    }


}
