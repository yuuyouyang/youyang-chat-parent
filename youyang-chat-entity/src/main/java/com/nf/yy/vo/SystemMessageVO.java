package com.nf.yy.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author smile
 */
@Data
@ToString
public class SystemMessageVO {

    /** 消息撤回提示 */
    public static final Integer MESSAGE_RECALL = 0;
    /** 用户上线提醒 */
    public static final Integer USER_ONLINE_REMIND = 1;
    /** 用户离线提醒 */
    public static final Integer USER_OFFLINE_REMIND = 2;
    /** 加入群聊提示 */
    public static final Integer JOIN_GROUP_CHAT = 3;
    /** 退出群聊提示 */
    public static final Integer EXIT_GROUP_CHAT = 4;

    /** object-id */
    private String objectId;
    /** object-type */
    private String objectType;
    /** 消息id */
    private String messageUuid;
    /** 请求方用户昵称 */
    private String requestNick;
    /** 响应方用户昵称 */
    private String responseNick;
    /**  消息类型 */
    private Integer messageType;

}
