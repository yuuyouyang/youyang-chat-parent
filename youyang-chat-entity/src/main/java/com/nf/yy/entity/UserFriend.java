package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表示一条好友记录
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserFriend {

    /** 用户ID */
    private String userId;
    /** 好友ID */
    private String friendId;
    /** 好友备注昵称 */
    private String friendNick;

}
