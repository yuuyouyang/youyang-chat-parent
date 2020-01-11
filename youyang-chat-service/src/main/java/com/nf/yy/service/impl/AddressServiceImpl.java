package com.nf.yy.service.impl;

import com.nf.yy.dao.AddressDao;
import com.nf.yy.entity.Address;
import com.nf.yy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author smile
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    /** 根据父级地址获取指定市级、县级地址 */
    @Override
    public List<Address> findByParentId(String parentId) {
        return addressDao.findByParentId(parentId);
    }

    /** 根据地址编码获取地址信息 */
    @Override
    public Address query(String codeId) {
        return addressDao.query(codeId);
    }

}
