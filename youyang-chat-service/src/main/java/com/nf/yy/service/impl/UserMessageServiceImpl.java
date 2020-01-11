package com.nf.yy.service.impl;

import com.nf.yy.dao.UserInfoDao;
import com.nf.yy.dao.UserMessageDao;
import com.nf.yy.entity.GroupMessage;
import com.nf.yy.entity.UserInfo;
import com.nf.yy.entity.UserMessage;
import com.nf.yy.service.UserMessageService;
import com.nf.yy.vo.ChatMessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smile
 */
@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private UserMessageDao userMessageDao;
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 根据房间Id获取当前好友的历史消息（分页查询）
     */
    @Override
    public List<ChatMessageVO> pageQueryMessage(int pageNum, int pageSize, String userId, String friendId) {
        return encapsulationList(userMessageDao.pageQueryMessage(pageNum, pageSize, userId, friendId));
    }

    /**
     * 获取与指定好友所有的图片类型信息
     */
    @Override
    public List<ChatMessageVO> getImgMessage(String userId, String friendId) {
        return encapsulationList(userMessageDao.getImgMessage(userId, friendId));
    }

    /**
     * 获取与指定好友最后50条消息
     */
    @Override
    public List<ChatMessageVO> getLatestMessage(String userId, String friendId) {
        return encapsulationList(userMessageDao.getLatestMessage(userId, friendId));
    }

    /**
     * 获取与指定好友最新一条消息
     */
    @Override
    public ChatMessageVO getLastMessage(String userId, String friendId) {
        return encapsulation(userMessageDao.getLastMessage(userId, friendId));
    }

    /**
     * 修改消息状态
     */
    @Override
    public Integer updateState(String userId, String friendId) {
        return userMessageDao.updateState(userId, friendId);
    }

    /**
     * 往数据库添加新消息
     */
    @Override
    public Integer insertMessage(UserMessage userMessage) {
        return userMessageDao.insertMessage(userMessage);
    }

    /**
     * 删除数据，用于消息撤回
     */
    @Override
    public Integer deleteMessage(String uuid) {
        return userMessageDao.deleteMessage(uuid);
    }

    /**
     * 根据uuid获取好友消息
     */
    @Override
    public ChatMessageVO findByUuid(String uuid) {
        return encapsulation(userMessageDao.findByUuid(uuid));
    }

    /**
     * 把List<GroupMessage>封装成List<ChatMessageVO>
     */
    private List<ChatMessageVO> encapsulationList(List<UserMessage> userMessageList) {
        List<ChatMessageVO> chatMessageVOS = new ArrayList<>();
        for (UserMessage userMessage : userMessageList) {
            chatMessageVOS.add(encapsulation(userMessage));
        }
        return chatMessageVOS;
    }

    /**
     * 把GroupMessage封装成ChatMessageVO
     */
    public ChatMessageVO encapsulation(UserMessage userMessage) {
        UserInfo userInfo = userInfoDao.findByUserId(userMessage.getUserId());
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        BeanUtils.copyProperties(userMessage, chatMessageVO);
        chatMessageVO.setObjectId(userMessage.getFriendId());
        chatMessageVO.setObjectType(1);
        chatMessageVO.setUserNick(userInfo.getUserNick());
        chatMessageVO.setHeadPortrait(userInfo.getHeadPortrait());
        return chatMessageVO;
    }

}
