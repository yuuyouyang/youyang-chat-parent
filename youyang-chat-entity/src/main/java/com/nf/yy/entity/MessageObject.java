package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户的消息列表、聊天对象
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageObject {

    /** 所属用户id */
    private String userId;
    /** 对象id */
    private String objectId;
    /** 对象类型；0-群对象；1-好友对象 */
    private Integer objectType;

}
