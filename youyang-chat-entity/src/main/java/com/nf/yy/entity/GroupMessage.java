package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 表示一条群消息、群聊天记录
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessage {

    /** 消息的唯一ID */
    private String messageUuid;
    /** 发送消息的时间 */
    private Date messageTime;
    /** 发送群ID */
    private String groupId;
    /** 发送用户ID */
    private String userId;
    /** 消息类型 */
    private String messageType;
    /** 需发送的消息 */
    private String messageContent;

}
