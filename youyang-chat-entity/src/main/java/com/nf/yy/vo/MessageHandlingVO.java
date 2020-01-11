package com.nf.yy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageHandlingVO {

    /** 发起请求的用户ID */
    private String userId;
    /** 接收请求的用户ID */
    private String friendId;
    /** 用户昵称 */
    private String userNick;
    /** 好友申请时的验证消息 */
    private String additionalMessage;
    /** 请求发起与处理时间 */
    private Date handlingTime;
    /** 处理状态 */
    private Boolean handlingState;
    /** 处理结果 */
    private Boolean handlingResults;

}
