package com.nf.yy.dao;

import com.nf.yy.entity.GroupMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 群成员管理
 * @author smile
 */
public interface GroupMemberDao {

    /** 根据群id获取所有群成员信息 */
    List<GroupMember> findByGroupId(String groupId);

    /** 根据用户id获取所有群成员信息 */
    List<GroupMember> findByUserId(String userId);

    /** 获取指定群指定成员信息 */
    GroupMember queryMember(@Param("groupId") String groupId, @Param("memberId") String memberId);

    /** 移除群成员 */
    Integer deleteMember(@Param("groupId") String groupId, @Param("memberId") String memberId);

    /** 添加群成员 */
    Integer insertMember(GroupMember groupMember);

    /** 修改群成员资料（群内昵称、状态） */
    Integer updateMember(GroupMember groupMember);

}
