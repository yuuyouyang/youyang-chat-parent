package com.nf.yy.service.impl;

import com.nf.yy.dao.QuotationDao;
import com.nf.yy.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author smile
 */
@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationDao quotationDao;

    /**
     * 插入语录
     */
    @Override
    public Integer insert(String message) {
        return quotationDao.insert(message);
    }

    /**
     * 随机获取一条语录
     */
    @Override
    public String quotation() {
        return quotationDao.quotation();
    }

}
