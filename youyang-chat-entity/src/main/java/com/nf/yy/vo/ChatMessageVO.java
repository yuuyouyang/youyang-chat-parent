package com.nf.yy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nf.yy.util.TimeUtils;
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
public class ChatMessageVO {

    /** 消息ID */
    private String messageUuid;
    /** 用户ID，发送者 */
    private String userId;
    /** 接收消息对象的ID */
    private String objectId;
    /** 对象类型 0、群聊 1、好友 */
    private Integer objectType;
    /** 用户昵称，发送者 */
    private String userNick;
    /** 用户头像，发送者 */
    private String headPortrait;
    /** 发送时间，发送者 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date messageTime;
    private String timeString;
    /** 消息类型 如 text、image等 */
    private String messageType;
    /** 消息内容 */
    private String messageContent;

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
        this.timeString = TimeUtils.formatTime(messageTime.getTime());
    }

}
