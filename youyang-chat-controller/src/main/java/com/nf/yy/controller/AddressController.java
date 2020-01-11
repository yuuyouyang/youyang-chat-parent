package com.nf.yy.controller;

import com.nf.yy.service.impl.AddressServiceImpl;
import com.nf.yy.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author smile
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    /**
     * 根据父级地址获取指定市级、县级地址
     */
    @GetMapping("/{parentId}")
    public ResponseVO findByParentId(@PathVariable String parentId) {
        return ResponseVO.success(addressService.findByParentId(parentId));
    }

    /**
     * 根据地址编码获取地址信息
     */
    @GetMapping("/code/{codeId}")
    public ResponseVO query(@PathVariable String codeId) {
        return ResponseVO.success(addressService.query(codeId));
    }

}
