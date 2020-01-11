package com.nf.yy.service;

import com.nf.yy.entity.GroupMessage;
import com.nf.yy.vo.ChatMessageVO;

import java.util.List;

/**
 * @author smile
 */
public interface GroupMessageService {

    /** 根据房间Id获取当前群聊的历史消息（分页查询） */
    List<ChatMessageVO> pageQueryMessage(int pageNum, int pageSize, String groupId);

    /** 获取指定群下所有的图片类型信息 */
    List<ChatMessageVO> getImgMessage(String groupId);

    /** 根据群id获取该群最后50条消息 */
    List<ChatMessageVO> getLatestMessage(String groupId);

    /** 根据ID获取最新一条消息 */
    ChatMessageVO getLastMessage(String groupId);

    /** 往数据库添加新消息 */
    Integer insertMessage(GroupMessage groupMessage);

    /** 删除数据，用于消息撤回 */
    Integer deleteMessage(String uuid);

    /** 根据uuid获取群消息 */
    ChatMessageVO findByUuid(String uuid);

}
