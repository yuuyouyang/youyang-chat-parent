package com.nf.yy.service;

import com.nf.yy.entity.GroupMessage;
import com.nf.yy.entity.UserMessage;
import com.nf.yy.vo.ChatMessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author smile
 */
public interface UserMessageService {

    /** 根据房间Id获取当前好友的历史消息（分页查询） */
    List<ChatMessageVO> pageQueryMessage(
            @Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
            @Param("userId") String userId, @Param("friendId") String friendId
    );

    /** 获取与指定好友所有的图片类型信息 */
    List<ChatMessageVO> getImgMessage(@Param("userId") String userId, @Param("friendId") String friendId);

    /** 获取与指定好友最后50条消息 */
    List<ChatMessageVO> getLatestMessage(@Param("userId") String userId, @Param("friendId") String friendId);

    /** 获取与指定好友最新一条消息 */
    ChatMessageVO getLastMessage(@Param("userId") String userId, @Param("friendId") String friendId);

    /** 修改消息状态 */
    Integer updateState(@Param("userId") String userId, @Param("friendId") String friendId);

    /** 往数据库添加新消息 */
    Integer insertMessage(UserMessage userMessage);

    /** 删除数据，用于消息撤回 */
    Integer deleteMessage(String uuid);

    /** 根据uuid获取好友消息 */
    ChatMessageVO findByUuid(String uuid);

}
