package com.nf.yy.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author smile
 */
@Data
@ToString
public class MessageObjectVO {

    /** 所属用户id */
    private String userId;
    /** 对象id */
    private String objectId;
    /** 对象类型；0-群对象；1-好友对象 */
    private Integer objectType;
    /** 对象头像 */
    private String objectHead;
    /** 对象名称 */
    private String objectName;
    /** 群主大大 */
    private String groupAdmin;

}
