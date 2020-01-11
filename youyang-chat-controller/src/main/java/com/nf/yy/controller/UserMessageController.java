package com.nf.yy.controller;

import com.github.pagehelper.PageInfo;
import com.nf.yy.service.impl.UserMessageServiceImpl;
import com.nf.yy.vo.ChatMessageVO;
import com.nf.yy.vo.ResponseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author smile
 */
@RestController
@RequestMapping("/api/user/message")
public class UserMessageController extends MessageController {

    @Autowired
    private UserMessageServiceImpl userMessageService;

    /**
     * 根据房间Id获取当前好友的历史消息（分页查询）
     */
    @GetMapping("/page")
    public ResponseVO pageQueryMessage(Authentication authentication, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, String friendId) {
        List<ChatMessageVO> chatMessageVOS = userMessageService.pageQueryMessage(pageNum, 50, authentication.getName(), friendId);
        PageInfo<ChatMessageVO> messageVOPageInfo = new PageInfo<>(chatMessageVOS, 5);
        return ResponseVO.success(messageVOPageInfo);
    }

    /**
     * 获取与指定好友所有的图片类型信息
     */
    @GetMapping("/query/img/{friendId}")
    public ResponseVO getImgMessage(Authentication authentication, @PathVariable String friendId) {
        return ResponseVO.success(userMessageService.getImgMessage(authentication.getName(), friendId));
    }

    /**
     * 获取与指定好友最后50条消息
     */
    @GetMapping("/query/latest/{friendId}")
    public ResponseVO getLatestMessage(Authentication authentication, @PathVariable String friendId) {
        return ResponseVO.success(userMessageService.getLatestMessage(authentication.getName(), friendId));
    }

    /**
     * 获取与指定好友最新一条消息
     */
    @GetMapping("/query/last/{friendId}")
    public ResponseVO getLastMessage(Authentication authentication, @PathVariable String friendId) {
        return ResponseVO.success(userMessageService.getLastMessage(authentication.getName(), friendId));
    }

    /**
     * 修改消息状态
     */
    @PutMapping("/state/{friendId}")
    public ResponseVO updateState(Authentication authentication, @PathVariable String friendId) {
        return ResponseVO.success(userMessageService.updateState(authentication.getName(), friendId));
    }

    /**
     * 根据uuid获取好友消息
     */
    @GetMapping("/{uuid}")
    public ResponseVO findByUuid(@PathVariable String uuid) {
        return ResponseVO.success(userMessageService.findByUuid(uuid));
    }

    /**
     * 将群消息从数据库移除，用于群消息撤回
     */
    @DeleteMapping("/{messageUuid}")
    public ResponseVO deleteMessage(@PathVariable String messageUuid, Authentication authentication) {
        ChatMessageVO chatMessageVO = userMessageService.findByUuid(messageUuid);
        int count = userMessageService.deleteMessage(messageUuid);
        return getResponseVO(authentication.getName(), true, count, chatMessageVO);
    }

}
