package com.nf.yy.controller;

import com.nf.yy.entity.UserFriend;
import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.impl.UserFriendServiceImpl;
import com.nf.yy.service.impl.UserInfoServiceImpl;
import com.nf.yy.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smile
 */
@RestController
@RequestMapping("/api/user/friend")
public class UserFriendController {

    @Autowired
    private UserFriendServiceImpl userFriendService;
    @Autowired
    private UserInfoServiceImpl userInfoService;

    /**
     * 根据用户ID查询好友列表
     */
    @GetMapping("")
    public ResponseVO queryAll(Authentication authentication) {
        List<UserInfo> userInfoList = new ArrayList<>();
        // 获取所有好友信息
        List<UserFriend> userFriendList = userFriendService.querys(authentication.getName());
        // 根据群id获取所有加入群的群信息
        for (UserFriend userFriend : userFriendList) {
            userInfoList.add(userInfoService.findByUserId(userFriend.getFriendId()));
        }
        return ResponseVO.success(userInfoList);
    }

    /**
     * 查询指定好友资料
     */
    @GetMapping("/{friendId}")
    public ResponseVO query(Authentication authentication, @PathVariable String friendId) {
        return ResponseVO.success(userFriendService.query(authentication.getName(), friendId));
    }

    /**
     * 添加到好友列表，绑定好友关系
     */
    @PostMapping("")
    public ResponseVO insert(UserFriend userFriend) {
        if (userFriendService.insert(userFriend) > 0) {
            return ResponseVO.success("添加好友成功！");
        } else {
            return ResponseVO.success("遇到未知错误！");
        }
    }

    /**
     * 解除好友关系
     */
    @DeleteMapping("/{friendId}")
    public ResponseVO delete(Authentication authentication, @PathVariable String friendId) {
        if (userFriendService.delete(authentication.getName(), friendId) > 0) {
            return ResponseVO.success("已与" + friendId + "解除好友关系！");
        } else {
            return ResponseVO.failed("遇到未知错误！");
        }
    }

}
