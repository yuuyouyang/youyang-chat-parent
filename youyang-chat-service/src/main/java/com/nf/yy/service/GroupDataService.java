package com.nf.yy.service;

import com.nf.yy.entity.GroupData;

import java.util.List;

/**
 * 群信息表
 * @author smile
 */
public interface GroupDataService {

    /** 创建一个新群，插入数据库 */
    Integer insert(GroupData groupData);

    /** 根据用户id修改用户资料 */
    Integer update(GroupData groupData);

    /** 根据群id查询群资料 */
    GroupData findByGroupId(String groupId);

    /** 根据用户id查询所管理的资料 */
    GroupData findByUserId(String userId);

    /** 根据关键字搜索群 */
    List<GroupData> search(String keyboard);

    /** 从数据库移除指定群 */
    Integer delete(String groupId);

    /** 随机从数据库获取十条数据 */
    List<GroupData> randomQuery();

}
