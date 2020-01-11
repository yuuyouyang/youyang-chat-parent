package com.nf.yy.service.impl;

import com.nf.yy.dao.GroupMemberDao;
import com.nf.yy.entity.GroupMember;
import com.nf.yy.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author smile
 */
@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberDao groupMemberDao;

    /**
     * 根据群id获取所有群成员信息
     */
    @Override
    public List<GroupMember> findByGroupId(String groupId) {
        return groupMemberDao.findByGroupId(groupId);
    }

    /**
     * 根据用户id获取所有群成员信息
     */
    @Override
    public List<GroupMember> findByUserId(String userId) {
        return groupMemberDao.findByUserId(userId);
    }

    /**
     * 获取指定群指定成员信息
     */
    @Override
    public GroupMember queryMember(String groupId, String memberId) {
        return groupMemberDao.queryMember(groupId, memberId);
    }

    /**
     * 移除群成员
     */
    @Override
    public Integer deleteMember(String groupId, String memberId) {
        return groupMemberDao.deleteMember(groupId, memberId);
    }

    /**
     * 添加群成员
     */
    @Override
    public Integer insertMember(GroupMember groupMember) {
        return groupMemberDao.insertMember(groupMember);
    }

    /**
     * 修改群成员资料（群内昵称、状态）
     */
    @Override
    public Integer updateMember(GroupMember groupMember) {
        return groupMemberDao.updateMember(groupMember);
    }

}
