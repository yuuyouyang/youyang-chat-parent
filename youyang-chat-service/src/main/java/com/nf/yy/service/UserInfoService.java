package com.nf.yy.service;

import com.nf.yy.entity.UserInfo;

import java.io.IOException;
import java.util.List;

/**
 * 用户信息
 * @author smile
 */
public interface UserInfoService {

    /** 增加用户,用户注册 */
    Integer register(UserInfo userInfo) throws IOException;

    /** 根据用户id查询 */
    UserInfo findByUserId(String userId);

    /** 根据用户id修改用户资料 */
    Integer update(UserInfo userInfo);

    /** 根据关键字查找用户 */
    List<UserInfo> search(String keyboard);

    /** 从数据库随机获取十条数据 */
    List<UserInfo> randomQuery();

}
