package com.nf.yy.service;

/**
 * @author smile
 */
public interface QuotationService {

    /** 插入语录 */
    Integer insert(String message);

    /** 随机获取一条语录 */
    String quotation();

}
