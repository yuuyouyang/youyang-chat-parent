package com.nf.yy.dao;

import com.nf.yy.entity.MessageHandling;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author smile
 */
public interface MessageHandlingDao {

    /** 查询所有用户需要处理的消息 */
    List<MessageHandling> queryAll(String userId);

    /** 查询数据库是否已有未处理的事务 */
    MessageHandling query(@Param("userId") String userId, @Param("friendId") String friendId);

    /** 新增事件处理消息 */
    Integer insert(MessageHandling messageHandling);

    /** 修改事务状态与结果 */
    Integer update(MessageHandling messageHandling);

}
