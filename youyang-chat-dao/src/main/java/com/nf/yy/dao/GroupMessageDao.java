package com.nf.yy.dao;

import com.nf.yy.entity.GroupMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author smile
 */
public interface GroupMessageDao {

    /** 根据房间Id获取当前群聊的历史消息（分页查询） */
    List<GroupMessage> pageQueryMessage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("groupId") String groupId);

    /** 获取指定群下所有的图片类型信息 */
    List<GroupMessage> getImgMessage(String groupId);

    /** 根据群id获取该群最后50条消息 */
    List<GroupMessage> getLatestMessage(String groupId);

    /** 根据群id获取最新一条消息 */
    GroupMessage getLastMessage(String groupId);

    /** 往数据库添加新消息 */
    Integer insertMessage(GroupMessage groupMessage);

    /** 删除数据，用于消息撤回 */
    Integer deleteMessage(String uuid);

    /** 根据uuid获取群消息 */
    GroupMessage findByUuid(String uuid);

}
