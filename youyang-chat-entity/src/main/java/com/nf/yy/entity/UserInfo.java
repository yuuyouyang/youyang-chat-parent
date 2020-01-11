package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

/**
 * 用户资料、信息
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements UserDetails {

    /** 用户账号、手机号 */
    @Length(min = 11, max = 11, message = "账号或手机号长度只能是11位")
    private String userId;
    /** 登录密码 */
    @Length(min = 8, max = 16, message = "密码长度必须在8-16个字符之间")
    private String userPassword;
    /** 用户头像 */
    private String headPortrait;
    /** 用户性别 */
    private Boolean userSex;
    /** 用户昵称 */
    @Length(min = 1, max = 20, message = "昵称长度必须在1-20个字符之间")
    private String userNick;
    /** 个性签名 */
    @Length(max = 20, message = "个性签名长度不能大于150个字符")
    private String userSignature;
    /** 所属省份ID */
    @Length(max = 6, message = "省份编码只能为6位字符")
    private String provinceId;
    /** 所属城市ID */
    @Length(max = 6, message = "城市编码只能为6位字符")
    private String cityId;
    /** 所属县区ID */
    @Length(max = 6, message = "县区编码只能为6位字符")
    private String countyId;
    /** 用户状态ID */
    @Min(value = 1, message = "用户状态必须在1-3之间")
    @Max(value = 3, message = "用户状态必须在1-3之间")
    private Integer stateId;

    public UserInfo(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public UserInfo(String userId, String userPassword, String headPortrait, Boolean userSex, String userNick, Integer stateId) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.headPortrait = headPortrait;
        this.userSex = userSex;
        this.userNick = userNick;
        this.stateId = stateId;
    }

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    /** 账户是否未过期 */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** 账户是否未被锁 */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
