package com.nf.yy.service;

import com.nf.yy.entity.MessageObject;
import com.nf.yy.vo.MessageObjectVO;

import java.util.List;

/**
 * @author smile
 */
public interface MessageObjectService {

    /** 根用户id获取消息列表 */
    List<MessageObjectVO> findByUserId(String userId);

    /** 插入一个新消息对象 */
    int insert(MessageObject messageObject);

    /** 从数据库移除一个消息对象 */
    int delete(MessageObject messageObject);

}
