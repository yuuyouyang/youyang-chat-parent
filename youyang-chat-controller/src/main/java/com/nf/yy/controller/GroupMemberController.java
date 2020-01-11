package com.nf.yy.controller;

import com.nf.yy.vo.SystemMessageVO;
import com.nf.yy.vo.UserInfoVO;
import com.nf.yy.entity.GroupMember;
import com.nf.yy.vo.ResponseVO;
import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.impl.GroupMemberServiceImpl;
import com.nf.yy.service.impl.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smile
 */
@RestController
@RequestMapping("/api/group/member")
public class GroupMemberController extends MessageController {

    @Autowired
    private GroupMemberServiceImpl groupMemberService;

    /**
     * 获取指定群所有成员信息
     */
    @GetMapping("/{groupId}")
    public ResponseVO queryMembers(@PathVariable String groupId) {
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        List<GroupMember> groupMemberList = groupMemberService.findByGroupId(groupId);
        for (GroupMember groupMember : groupMemberList) {
            UserInfo userInfo = userInfoService.findByUserId(groupMember.getMemberId());
            userInfoVOList.add(encapsulation(groupMember, userInfo));
        }
        return ResponseVO.success(userInfoVOList);
    }

    /**
     * 获取指定群指定成员信息
     */
    @GetMapping("/query/{groupId}/{memberId}")
    public ResponseVO queryMember(@PathVariable String groupId, @PathVariable String memberId) {
        GroupMember groupMember = groupMemberService.queryMember(groupId, memberId);
        UserInfo userInfo = userInfoService.findByUserId(groupMember.getMemberId());
        return ResponseVO.success(encapsulation(groupMember, userInfo));
    }

    /**
     * 封装一个UserInfoVO
     */
    private UserInfoVO encapsulation(GroupMember groupMember, UserInfo userInfo) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserId(groupMember.getMemberId());
        userInfoVO.setUserNick(groupMember.getMemberNick());
        userInfoVO.setStateId(groupMember.getStateId());
        userInfoVO.setHeadPortrait(userInfo.getHeadPortrait());
        return userInfoVO;
    }

    /**
     * 移除群成员
     */
    @DeleteMapping("/{groupId}/{memberId}")
    public ResponseVO deleteMember(@PathVariable String groupId, @PathVariable String memberId) {
        GroupMember groupMember = groupMemberService.queryMember(groupId, memberId);
        if (groupMemberService.deleteMember(groupId, memberId) > 0) {
            // 指定消息类型位退群提示
            encapsulation(groupMember, SystemMessageVO.EXIT_GROUP_CHAT);
            return ResponseVO.success("群成员移除成功！");
        } else {
            return ResponseVO.failed("群成员移除失败");
        }
    }

    /**
     * 添加群成员
     */
    @PostMapping("")
    public ResponseVO insertMember(GroupMember groupMember) {
        GroupMember groupMemberTemp = groupMemberService.queryMember(groupMember.getGroupId(), groupMember.getMemberId());
        if (groupMemberTemp != null) {
            return ResponseVO.failed("你已是群成员，无需重复加入！");
        }
        if (groupMemberService.insertMember(groupMember) > 0) {
            // 指定消息类型位进群提示
            encapsulation(groupMember, SystemMessageVO.JOIN_GROUP_CHAT);
            return ResponseVO.success("加入群聊成功！");
        } else {
            return ResponseVO.failed("加入群聊失败！");
        }
    }

    /**
     * 修改群成员资料（群内昵称、状态）
     */
    @PutMapping("/{groupId}/{memberId}")
    public ResponseVO updateMember(@RequestBody GroupMember groupMember) {
        if (groupMemberService.updateMember(groupMember) > 0) {
            return ResponseVO.success("成员资料修改成功！");
        } else {
            return ResponseVO.failed("成员资料修改失败！");
        }
    }

    /**
     * 封装一个GroupSystemMessage并推送到客户端
     * 推送到/群id/systemMessage
     */
    private void encapsulation(GroupMember groupMember, Integer messageType) {
        SystemMessageVO systemMessageVO = new SystemMessageVO();
        // 指定消息类型为加入群聊提示
        systemMessageVO.setMessageType(messageType);
        systemMessageVO.setResponseNick(groupMember.getMemberNick());
        systemMessageVO.setObjectId(groupMember.getGroupId());
        simpMessageSendingOperations.convertAndSendToUser(groupMember.getGroupId(), "/system/message", systemMessageVO);
    }

}
