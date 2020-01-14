package com.nf.yy.service.impl;

import com.nf.yy.dao.MessageHandlingDao;
import com.nf.yy.dao.UserInfoDao;
import com.nf.yy.entity.MessageHandling;
import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.MessageHandlingService;
import com.nf.yy.vo.MessageHandlingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smile
 */
@Service
public class MessageHandlingServiceImpl implements MessageHandlingService {

    @Autowired
    private MessageHandlingDao messageHandlingDao;
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 查询用户需要处理的消息
     */
    @Override
    public List<MessageHandlingVO> queryAll(String userId) {
        return encapsulationList(messageHandlingDao.queryAll(userId), userId);
    }

    /**
     * 查询数据库是否已有未处理的事务
     */
    @Override
    public MessageHandling query(String userId, String friendId) {
        return messageHandlingDao.query(userId, friendId);
    }

    /**
     * 新增事件处理消息
     */
    @Override
    public Integer insert(MessageHandling messageHandling) {
        return messageHandlingDao.insert(messageHandling);
    }

    /**
     * 修改事务状态与结果
     */
    @Override
    public Integer update(MessageHandling messageHandling) {
        return messageHandlingDao.update(messageHandling);
    }

    /**
     * 封装一个List<MessageHandlingVO>
     */
    public List<MessageHandlingVO> encapsulationList(List<MessageHandling> messageHandlingList, String userId) {
        List<MessageHandlingVO> messageHandlingVOList = new ArrayList<>();
        for (MessageHandling messageHandling : messageHandlingList) {
            MessageHandlingVO messageHandlingVO = new MessageHandlingVO();
            BeanUtils.copyProperties(messageHandling, messageHandlingVO);
            String objectId = "";
            if(messageHandling.getUserId().equals(userId)){
                objectId =  messageHandling.getFriendId();
            }
            if(messageHandling.getFriendId().equals(userId)){
                objectId =  messageHandling.getUserId();
            }
            UserInfo userInfo = userInfoDao.findByUserId(objectId);
            messageHandlingVO.setUserNick(userInfo.getUserNick());
            messageHandlingVOList.add(messageHandlingVO);
        }
        return messageHandlingVOList;
    }

}
