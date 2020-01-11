package com.nf.yy.controller;

import com.nf.yy.entity.MessageObject;
import com.nf.yy.vo.ResponseVO;
import com.nf.yy.service.impl.MessageObjectServiceImpl;
import com.nf.yy.vo.MessageObjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


/**
 * @author smile
 */
@RestController
@RequestMapping("/api/message/objects")
public class MessageObjectController {

    @Autowired
    private MessageObjectServiceImpl messageObjectService;

    /**
     * 获取当前登录用户的ID
     */
    @GetMapping("")
    public ResponseVO findByUserId(Authentication authentication) {
        return ResponseVO.success(messageObjectService.findByUserId(authentication.getName()));
    }

    /**
     * 根据userId与objectId获取消息列表
     */
    @GetMapping("/query")
    public ResponseVO findByObjectId(MessageObject messageObject, Authentication authentication) {
        messageObject.setUserId(authentication.getName());
        MessageObjectVO messageObjectVO = messageObjectService.findByObjectId(messageObject);
        return ResponseVO.success(messageObjectVO);
    }

    /**
     * 插入一个新消息对象
     */
    @PostMapping("")
    public ResponseVO insert(String objectId, Integer objectType, Authentication authentication) {
        MessageObject messageObject = new MessageObject(authentication.getName(), objectId, objectType);
        if (messageObjectService.insert(messageObject) > 0) {
            return ResponseVO.success("消息对象插入成功！");
        } else {
            return ResponseVO.failed("消息对象插入失败！");
        }
    }

    /**
     * 从数据库移除一个消息对象
     */
    @DeleteMapping("/{objectId}")
    public ResponseVO delete(@PathVariable String objectId, Authentication authentication) {
        MessageObject messageObject = new MessageObject();
        messageObject.setUserId(authentication.getName());
        messageObject.setObjectId(objectId);
        if (messageObjectService.delete(messageObject) > 0) {
            return ResponseVO.success("消息对象移除成功！");
        } else {
            return ResponseVO.failed("消息对象移除失败！");
        }
    }

}
