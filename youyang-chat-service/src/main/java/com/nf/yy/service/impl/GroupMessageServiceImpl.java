package com.nf.yy.service.impl;

import com.nf.yy.dao.GroupMessageDao;
import com.nf.yy.dao.UserInfoDao;
import com.nf.yy.entity.GroupMessage;
import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.GroupMessageService;
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
public class GroupMessageServiceImpl implements GroupMessageService {

    @Autowired
    private GroupMessageDao groupMessageDao;
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 根据房间Id获取当前群聊的历史消息（分页查询）
     */
    @Override
    public List<ChatMessageVO> pageQueryMessage(int pageNum, int pageSize, String groupId) {
        return encapsulationList(groupMessageDao.pageQueryMessage(pageNum, pageSize, groupId));
    }

    /**
     * 获取指定群下所有的图片类型信息
     */
    @Override
    public List<ChatMessageVO> getImgMessage(String groupId) {
        return encapsulationList(groupMessageDao.getImgMessage(groupId));
    }

    /**
     * 根据群id获取该群最后50条消息
     */
    @Override
    public List<ChatMessageVO> getLatestMessage(String groupId) {
        return encapsulationList(groupMessageDao.getLatestMessage(groupId));
    }

    /**
     * 根据ID获取最新一条消息
     */
    @Override
    public ChatMessageVO getLastMessage(String groupId) {
        GroupMessage groupMessage = groupMessageDao.getLastMessage(groupId);
        return encapsulation(groupMessage);
    }

    /**
     * 往数据库添加新消息
     */
    @Override
    public Integer insertMessage(GroupMessage groupMessage) {
        return groupMessageDao.insertMessage(groupMessage);
    }

    /**
     * 删除数据，用于消息撤回
     */
    @Override
    public Integer deleteMessage(String uuid) {
        return groupMessageDao.deleteMessage(uuid);
    }

    /**
     * 根据uuid获取群消息
     */
    @Override
    public ChatMessageVO findByUuid(String messageUuid) {
        return encapsulation(groupMessageDao.findByUuid(messageUuid));
    }

    /**
     * 把List<GroupMessage>封装成List<ChatMessageVO>
     */
    private List<ChatMessageVO> encapsulationList(List<GroupMessage> groupMessageList) {
        List<ChatMessageVO> chatMessageVOS = new ArrayList<>();
        for (GroupMessage groupMessage : groupMessageList) {
            chatMessageVOS.add(encapsulation(groupMessage));
        }
        return chatMessageVOS;
    }

    /**
     * 把GroupMessage封装成ChatMessageVO
     */
    public ChatMessageVO encapsulation(GroupMessage groupMessage) {
        UserInfo userInfo = userInfoDao.findByUserId(groupMessage.getUserId());
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        BeanUtils.copyProperties(groupMessage, chatMessageVO);
        chatMessageVO.setObjectId(groupMessage.getGroupId());
        chatMessageVO.setObjectType(0);
        chatMessageVO.setUserNick(userInfo.getUserNick());
        chatMessageVO.setHeadPortrait(userInfo.getHeadPortrait());
        return chatMessageVO;
    }

}
