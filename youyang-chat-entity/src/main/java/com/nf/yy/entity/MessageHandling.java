package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 表示一条需要处理或已经处理的事务信息
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageHandling {

    /** 发起请求的用户ID */
    private String userId;
    /** 接收请求的用户ID */
    private String friendId;
    /** 好友申请时的验证消息 */
    private String additionalMessage;
    /** 请求发起与处理时间 */
    private Date handlingTime;
    /** 处理状态 */
    private Boolean handlingState;
    /** 处理结果 */
    private Boolean handlingResults;

}
