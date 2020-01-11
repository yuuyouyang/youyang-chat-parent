package com.nf.yy.dao;

/**
 * 经典语录
 * @author smile
 */
public interface QuotationDao {

    /** 插入语录 */
    Integer insert(String message);

    /** 随机获取一条语录 */
    String quotation();

}
