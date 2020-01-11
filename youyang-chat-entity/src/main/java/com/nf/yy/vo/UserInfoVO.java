package com.nf.yy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author smile
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    private String userId;
    private String userNick;
    private String headPortrait;
    private Integer stateId;

}
