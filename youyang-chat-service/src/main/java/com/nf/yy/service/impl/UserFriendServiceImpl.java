package com.nf.yy.service.impl;

import com.nf.yy.dao.UserFriendDao;
import com.nf.yy.entity.UserFriend;
import com.nf.yy.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author smile
 */
@Service
public class UserFriendServiceImpl implements UserFriendService {

    @Autowired
    private UserFriendDao userFriendDao;

    /** 根据用户ID查询好友列表 */
    @Override
    public List<UserFriend> querys(String userId) {
        return userFriendDao.querys(userId);
    }

    /** 查询指定好友资料 */
    @Override
    public UserFriend query(String userId, String friendId) {
        return userFriendDao.query(userId, friendId);
    }

    /** 添加到好友列表，绑定好友关系 */
    @Override
    public Integer insert(UserFriend userFriend) {
        return userFriendDao.insert(userFriend);
    }

    /** 解除好友关系 */
    @Override
    public Integer delete(String userId, String friendId) {
        return userFriendDao.delete(userId, friendId);
    }

}
