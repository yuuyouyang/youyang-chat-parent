package com.nf.yy.dao;

import com.nf.yy.entity.MessageObject;

import java.util.List;

/**
 * @author smile
 */
public interface MessageObjectDao {

    /** 根用户id获取消息列表 */
    List<MessageObject> findByUserId(String userId);

    /** 插入一个新消息对象 */
    int insert(MessageObject messageObject);

    /** 从数据库移除一个消息对象 */
    int delete(MessageObject messageObject);

}
