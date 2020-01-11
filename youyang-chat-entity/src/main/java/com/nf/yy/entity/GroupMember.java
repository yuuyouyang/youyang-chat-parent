package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 群成员信息
 * @author smile
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember {

    /** 群ID */
    private String groupId;
    /** 成员（用户）ID */
    private String memberId;
    /** 成员（用户）群内昵称 */
    private String memberNick;
     /** 成员状态 1、正常 2、禁言 3、锁定 */
    private Integer stateId;

}
