package com.nf.yy.service;

import com.nf.yy.entity.GroupMember;

import java.util.List;

/**
 * 群成员管理
 *
 * @author smile
 */
public interface GroupMemberService {

    /** 根据群id获取所有群成员信息 */
    List<GroupMember> findByGroupId(String groupId);

    /** 根据用户id获取所有群成员信息 */
    List<GroupMember> findByUserId(String userId);

    /** 获取指定群指定成员信息 */
    GroupMember queryMember(String groupId, String memberId);

    /** 移除群成员 */
    Integer deleteMember(String groupId, String memberId);

    /** 添加群成员 */
    Integer insertMember(GroupMember groupMember);

    /** 修改群成员资料（群内昵称、状态） */
    Integer updateMember(GroupMember groupMember);

}
