package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 群信息
 * @author smile
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupData {

    /** 群ID */
    private String groupId;
    /** 群名称 */
    private String groupName;
    /** 创建时间 */
    private Date createTime;
    /** 创建人ID */
    private String groupAdmin;
    /** 群头像 */
    private String groupPortrait;
    /** 群介绍 */
    private String groupIntroduce;
    /** 群状态 1、正常 2、禁言 3、锁定 */
    private Integer stateId;

}
