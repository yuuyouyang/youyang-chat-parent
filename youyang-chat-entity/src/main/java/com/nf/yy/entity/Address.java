package com.nf.yy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 地址信息
 * @author smile
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /** 地址编码 */
    private String codeId ;
    /** 地址名称 */
    private String addressName ;
    /** 父级地址编码 */
    private String parentId ;

}