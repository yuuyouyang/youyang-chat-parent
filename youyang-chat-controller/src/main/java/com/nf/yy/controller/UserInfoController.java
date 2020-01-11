package com.nf.yy.controller;

import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.impl.AddressServiceImpl;
import com.nf.yy.service.impl.UserInfoServiceImpl;
import com.nf.yy.util.FileUtils;
import com.nf.yy.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author smile
 */
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoServiceImpl userInfoService;
    @Autowired
    private AddressServiceImpl addressService;

    /**
     * 增加用户，用户注册
     */
    @PostMapping("/user/register")
    public ResponseVO register(String userId, String password) throws IOException {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        if (userInfo != null) {
            return ResponseVO.failed("该账号已被注册！");
        }
        userInfo = new UserInfo(userId, password);
        if (userInfoService.register(userInfo) > 0) {
            return ResponseVO.success(userId + "注册成功！");
        } else {
            return ResponseVO.failed("注册失败，数据写入失败！");
        }
    }

    /**
     * 根据关键字搜索用户
     */
    @GetMapping("/api/user/query/search/{keyboard}")
    public ResponseVO search(@PathVariable String keyboard) {
        return ResponseVO.success(userInfoService.search(keyboard));
    }

    /**
     * 随机从数据库获取十条数据
     */
    @GetMapping("/api/user/query/random")
    public ResponseVO randomQuery() {
        return ResponseVO.success(userInfoService.randomQuery());
    }

    /**
     * 根据用户ID查询
     */
    @GetMapping("/api/user/{userId}")
    public ResponseVO findByUserId(@PathVariable String userId) {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        userInfo.setUserPassword("(～￣▽￣)～");
        userInfo.setProvinceId(addressService.query(userInfo.getProvinceId()).getAddressName());
        userInfo.setCityId(addressService.query(userInfo.getCityId()).getAddressName());
        userInfo.setCountyId(addressService.query(userInfo.getCountyId()).getAddressName());
        return ResponseVO.success(userInfo);
    }

    /**
     * 获取更新用户信息
     */
    @GetMapping("/api/user/information")
    public ResponseVO getCurrentUser(Authentication authentication, Boolean isCoding) {
        UserInfo userInfo = userInfoService.findByUserId(authentication.getName());
        userInfo.setUserPassword("(～￣▽￣)～");
        if (isCoding) {
            userInfo.setProvinceId(addressService.query(userInfo.getProvinceId()).getAddressName());
            userInfo.setCityId(addressService.query(userInfo.getCityId()).getAddressName());
            userInfo.setCountyId(addressService.query(userInfo.getCountyId()).getAddressName());
        }
        return ResponseVO.success(userInfo);
    }

    /**
     * 修改用户资料
     */
    @PostMapping("/api/user/{userId}")
    public ResponseVO update(UserInfo userInfo, MultipartFile file) throws IOException {
        if (file != null) {
            String saveFileName = "/head/" + FileUtils.createFileName(file.getOriginalFilename());
            file.transferTo(new File(FileUtils.ABSOLUTE_FILE_PATH + saveFileName));
            FileUtils.deleteFile(userInfo.getHeadPortrait());
            System.out.println(saveFileName);
            userInfo.setHeadPortrait(saveFileName);
        }
        System.out.println(userInfo);
        if (userInfoService.update(userInfo) > 0) {
            return ResponseVO.success("用户资料修改完成！");
        } else {
            return ResponseVO.failed("用户资料修改失败！");
        }
    }

}
