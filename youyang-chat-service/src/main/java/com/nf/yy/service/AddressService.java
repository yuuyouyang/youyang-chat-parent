package com.nf.yy.service;

import com.nf.yy.entity.Address;

import java.util.List;

/**
 * @author smile
 */
public interface AddressService {

    /** 根据父级地址获取指定市级、县级地址 */
    List<Address> findByParentId(String parentId);

    /** 根据地址编码获取地址信息 */
    Address query(String codeId);

}
