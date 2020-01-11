package com.nf.yy.service.impl;

import com.nf.yy.dao.UserInfoDao;
import com.nf.yy.entity.UserInfo;
import com.nf.yy.service.UserInfoService;
import com.nf.yy.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author smile
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户id查询用户信息
     */
    @Override
    public UserInfo findByUserId(String userId) {
        return userInfoDao.findByUserId(userId);
    }

    /**
     * 根据用户id修改用户资料
     */
    @Override
    public Integer update(UserInfo userInfo) {
        return userInfoDao.update(userInfo);
    }

    /**
     * 根据关键字查找用户
     */
    @Override
    public List<UserInfo> search(String keyboard) {
        return userInfoDao.search(keyboard);
    }

    /**
     * 从数据库随机获取十条数据
     */
    @Override
    public List<UserInfo> randomQuery() {
        return userInfoDao.randomQuery();
    }

    /**
     * 增加用户,用户注册
     */
    @Override
    public Integer register(UserInfo userInfo) throws IOException {
        return encryption(userInfo.getUserId(), userInfo.getUserPassword());
    }

    /**
     * 随机获取一个初始头像、PBKDF2密码加密
     */
    private Integer encryption(String userId, String password) throws IOException {
        // 获取头像地址,若文件不存在返回字符串false
        String headPortrait = createHeadPortrait();
        // 生成一个新的文件名作为头像地址
        String destPath = "/head/" + FileUtils.createFileName(".jpg");
        // 获取密文密码
        String cipherText = passwordEncoder.encode(password);
        Integer submitState = registerSubmit(userId, cipherText, destPath);
        if (!"false".equals(headPortrait) && submitState > 0) {
            FileUtils.copy(headPortrait, destPath);
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 保存注册信息、PBKDF2盐，注册成功后写入头像
     */
    private Integer registerSubmit(String userId, String password, String destPath) {
        UserInfo userInfo = new UserInfo(userId, password, destPath, true, userId, 1);
        return userInfoDao.register(userInfo);
    }

    /**
     * 新账号随机获取一个头像
     */
    private String createHeadPortrait() {
        int number = getIntRandom(0, 11);
        String srcPath = "/head/initial/" + number + ".jpg";
        if (FileUtils.existsFile(srcPath)) {
            return srcPath;
        } else {
            return "false";
        }
    }

    /**
     * @return 随机返一个指定区间的数字
     */
    public static int getIntRandom(int start, int end) {
        if (end < start) {
            int t = end;
            end = start;
            start = t;
        }
        return start + (int) (Math.random() * (end - start));
    }

}
