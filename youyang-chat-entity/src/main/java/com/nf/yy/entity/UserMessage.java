package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 表示一条好友消息记录
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserMessage {

    /** 消息ID */
    private String messageUuid;
    /** 发送消息的时间 */
    private Date messageTime;
    /** 发送消息用户的ID */
    private String userId;
    /** 接受消息用户的ID */
    private String friendId;
    /** 消息类型 */
    private String messageType;
    /** 发送的消息 */
    private String messageContent;
    /** 接受状态 */
    private Boolean messageStatus;

}
