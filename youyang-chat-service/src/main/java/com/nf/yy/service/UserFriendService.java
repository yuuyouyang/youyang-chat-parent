package com.nf.yy.service;

import com.nf.yy.entity.UserFriend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author smile
 */
public interface UserFriendService {

    /** 根据用户ID查询好友列表 */
    List<UserFriend> querys(String userId);

    /** 查询指定好友资料 */
    UserFriend query(@Param("userId") String userId, @Param("friendId") String friendId);

    /** 添加到好友列表，绑定好友关系 */
    Integer insert(UserFriend userFriend);

    /** 解除好友关系 */
    Integer delete(@Param("userId") String userId, @Param("friendId") String friendId);

}
