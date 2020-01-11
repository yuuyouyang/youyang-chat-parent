package com.nf.yy.service.impl;

import com.nf.yy.dao.GroupDataDao;
import com.nf.yy.dao.GroupMemberDao;
import com.nf.yy.dao.UserInfoDao;
import com.nf.yy.entity.GroupData;
import com.nf.yy.entity.GroupMember;
import com.nf.yy.service.GroupDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author smile
 */
@Service
public class GroupDataServiceImpl implements GroupDataService {

    @Autowired
    private GroupDataDao groupDataDao;
    @Autowired
    private GroupMemberDao groupMemberDao;
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 创建一个新群，插入数据库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(GroupData groupData) {
        groupData.setGroupId(GeneratingRandomNumbers(10));
        groupMemberDao.insertMember(getMemberInformation(groupData));
        return groupDataDao.insert(groupData);
    }

    /**
     * 根据用户id修改用户资料
     */
    @Override
    public Integer update(GroupData groupData) {
        return groupDataDao.update(groupData);
    }

    /**
     * 根据群id查询群资料
     */
    @Override
    public GroupData findByGroupId(String groupId) {
        return groupDataDao.findByGroupId(groupId);
    }

    /**
     * 根据用户id查询所管理的资料
     */
    @Override
    public GroupData findByUserId(String userId) {
        return groupDataDao.findByUserId(userId);
    }

    /**
     * 根据关键字搜索群
     */
    @Override
    public List<GroupData> search(String keyboard) {
        return groupDataDao.search(keyboard);
    }

    /**
     * 从数据库移除指定群
     */
    @Override
    public Integer delete(String groupId) {
        return groupDataDao.delete(groupId);
    }

    /**
     * 随机从数据库获取十条数据
     */
    @Override
    public List<GroupData> randomQuery() {
        return groupDataDao.randomQuery();
    }

    /**
     * 生成指定位数的随机数
     */
    private String GeneratingRandomNumbers(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        if (groupDataDao.findByGroupId(val) != null || val.substring(0, 1).equals('0')) {
            return GeneratingRandomNumbers(length);
        } else {
            return val;
        }
    }

    /**
     * 生成获取群成员信息
     */
    private GroupMember getMemberInformation(GroupData groupData) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupData.getGroupId());
        groupMember.setMemberId(groupData.getGroupAdmin());
        groupMember.setMemberNick(userInfoDao.findByUserId(groupData.getGroupAdmin()).getUserNick());
        groupMember.setStateId(1);
        return groupMember;
    }

}
