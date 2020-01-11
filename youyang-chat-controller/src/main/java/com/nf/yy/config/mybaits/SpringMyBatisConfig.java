package com.nf.yy.config.mybaits;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 整合spring与mybatis
 *
 * @author smile
 */
@Configuration
@MapperScan("com.nf.yy.dao")
@ComponentScan("com.nf.yy.service.impl")
@Import(DataSourceConfig.class)
public class SpringMyBatisConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 配置SqlSession工厂
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("classpath:mappers/*.xml");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(configuration());
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setPlugins(pageInterceptor());
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * mybaties配置维护类
     */
    public org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // configuration.setLogImpl(StdOutImpl.class);
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }

    /**
     * 配置mybatis分页插件
     */
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("reasonable", "true");
        properties.put("supportMethodsArguments", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}
