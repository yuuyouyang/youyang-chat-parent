package com.nf.yy.service.impl;

import com.nf.yy.dao.GroupDataDao;
import com.nf.yy.dao.MessageObjectDao;
import com.nf.yy.dao.UserInfoDao;
import com.nf.yy.entity.GroupData;
import com.nf.yy.entity.MessageObject;
import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.MessageObjectService;
import com.nf.yy.vo.MessageObjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smile
 */
@Service
public class MessageObjectServiceImpl implements MessageObjectService {

    @Autowired
    private MessageObjectDao messageObjectDao;
    @Autowired
    private GroupDataDao groupDataDao;
    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * 根用户id获取消息列表
     */
    @Override
    public List<MessageObjectVO> findByUserId(String userId) {
        List<MessageObjectVO> messageObjectVOList = new ArrayList<>();
        List<MessageObject> messageObjects = messageObjectDao.findByUserId(userId);
        for (MessageObject messageObject : messageObjects) {
            messageObjectVOList.add(encapsulation(messageObject));
        }
        return messageObjectVOList;
    }

    /**
     * 插入一个新消息对象
     */
    @Override
    public int insert(MessageObject messageObject) {
        return messageObjectDao.insert(messageObject);
    }

    /**
     * 从数据库移除一个消息对象
     */
    @Override
    public int delete(MessageObject messageObject) {
        return messageObjectDao.delete(messageObject);
    }

    /**
     * 根据userId与objectId获取消息列表
     */
    public MessageObjectVO findByObjectId(MessageObject messageObject) {
        return encapsulation(messageObject);
    }

    /**
     * 封装一个MessageObjectVO
     */
    public MessageObjectVO encapsulation(MessageObject messageObject) {
        MessageObjectVO messageObjectVO = new MessageObjectVO();
        BeanUtils.copyProperties(messageObject, messageObjectVO);
        if (messageObject.getObjectType() == 0) {
            GroupData groupData = groupDataDao.findByGroupId(messageObject.getObjectId());
            messageObjectVO.setObjectHead(groupData.getGroupPortrait());
            messageObjectVO.setObjectName(groupData.getGroupName());
        } else {
            UserInfo userInfo = userInfoDao.findByUserId(messageObject.getObjectId());
            messageObjectVO.setObjectHead(userInfo.getHeadPortrait());
            messageObjectVO.setObjectName(userInfo.getUserNick());
        }
        return messageObjectVO;
    }

}
