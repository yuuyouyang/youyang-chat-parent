package com.nf.yy.config.spring;

import com.nf.yy.config.mybaits.SpringMyBatisConfig;
import com.nf.yy.config.security.SpringSecurityConfig;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;

/**
 * @author smile
 */
@Configuration
@ComponentScan("com.nf.yy.config.stomp")
@EnableTransactionManagement(proxyTargetClass = true)
@Import({SpringMyBatisConfig.class, SpringSecurityConfig.class})
public class RootConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 文件上传支持
     * id必须为multipartResolver，否则无法解析form-data类型的数据
     */
    @Bean("multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() throws IOException {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 1024);
        commonsMultipartResolver.setMaxInMemorySize(1024 * 1024 * 1024);
        commonsMultipartResolver.setUploadTempDir(new FileSystemResource("/uploadTempDir"));
        commonsMultipartResolver.setResolveLazily(true);
        return commonsMultipartResolver;
    }

    /**
     * 数据校验配置
     * failFast的意思只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory =
                Validation.byProvider(HibernateValidator.class)
                        .configure()
                        .failFast(true)
                        .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource);
    }

}
