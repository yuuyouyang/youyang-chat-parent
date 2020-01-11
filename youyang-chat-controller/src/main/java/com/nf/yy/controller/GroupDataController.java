package com.nf.yy.controller;

import com.nf.yy.entity.GroupData;
import com.nf.yy.entity.GroupMember;
import com.nf.yy.vo.ResponseVO;
import com.nf.yy.service.impl.GroupDataServiceImpl;
import com.nf.yy.service.impl.GroupMemberServiceImpl;
import com.nf.yy.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author smile
 */
@RestController
@RequestMapping("/api/group/data")
public class GroupDataController {

    @Autowired
    private GroupDataServiceImpl groupDataService;
    @Autowired
    private GroupMemberServiceImpl groupMemberService;

    /**
     * 根据群id查询群资料
     */
    @GetMapping("/{groupId}")
    public ResponseVO findByGroupId(@PathVariable String groupId) {
        GroupData groupData = groupDataService.findByGroupId(groupId);
        if (groupData != null) {
            return ResponseVO.success(groupData);
        } else {
            return ResponseVO.failed("没有查到该群信息");
        }
    }

    /**
     * 随机从数据库获取十条数据
     */
    @GetMapping("/query/random")
    public ResponseVO randomQuery() {
        return ResponseVO.success(groupDataService.randomQuery());
    }

    /**
     * 根据用户id查询群资料
     */
    @GetMapping("/query/user-id/{userId}")
    public ResponseVO findByUserId(@PathVariable String userId) {
        return ResponseVO.success(groupDataService.findByUserId(userId));
    }

    /**
     * 根据用户ID获取所加入群的群信息
     */
    @GetMapping("/query/member-id/{memberId}")
    public ResponseVO findByJoinGroup(@PathVariable String memberId) {
        List<GroupData> groupDataList = new ArrayList<>();
        // 从成员表获取所有加入的群
        List<GroupMember> memberList = groupMemberService.findByUserId(memberId);
        // 根据群id获取所有加入群的群信息
        for (GroupMember groupMember : memberList) {
            groupDataList.add(groupDataService.findByGroupId(groupMember.getGroupId()));
        }
        return ResponseVO.success(groupDataList);
    }

    /**
     * 根据关键字搜索群
     */
    @GetMapping("/query/search/{keyboard}")
    public ResponseVO search(@PathVariable String keyboard) {
        return ResponseVO.success(groupDataService.search(keyboard));
    }

    /**
     * 创建一个新群，插入数据库
     */
    @PostMapping("")
    public ResponseVO insert(GroupData groupData, MultipartFile file) throws IOException {
        groupData = transferToFile(groupData, file);
        groupData.setCreateTime(new Date());
        groupData.setStateId(1);
        if (groupDataService.insert(groupData) > 0) {
            return ResponseVO.success(groupData.getGroupAdmin());
        } else {
            return ResponseVO.failed("新建群聊失败！");
        }
    }

    /**
     * 从数据库移除指定群
     */
    @DeleteMapping("/{groupId}")
    public ResponseVO delete(@PathVariable String groupId) {
        if (groupDataService.delete(groupId) > 0) {
            return ResponseVO.success("移除完成！");
        } else {
            return ResponseVO.failed("移除失败！");
        }
    }

    /**
     * 根据群id修改群资料
     */
    @PostMapping("/{groupId}")
    public ResponseVO update(GroupData groupData, MultipartFile file) throws IOException {
        groupData = transferToFile(groupData, file);
        if (groupDataService.update(groupData) > 0) {
            return ResponseVO.success("修改成功");
        } else {
            return ResponseVO.failed("修改失败！");
        }
    }

    /**
     * 写入文件、并返回文件地址
     */
    private GroupData transferToFile(GroupData groupData, MultipartFile file) throws IOException {
        if (file != null) {
            String saveFileName = "/group/" + FileUtils.createFileName(file.getOriginalFilename());
            file.transferTo(new File(FileUtils.ABSOLUTE_FILE_PATH + saveFileName));
            if (!"#head#".equals(groupData.getGroupPortrait())) {
                FileUtils.deleteFile(groupData.getGroupPortrait());
            }
            groupData.setGroupPortrait(saveFileName);
        }
        return groupData;
    }

}
