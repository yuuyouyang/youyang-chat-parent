package com.nf.yy.config.mybaits;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 配置数据库连接池Druid
 * @author smile
 */
@Configuration
@PropertySource(value = {"classpath:mysql-config.properties"}, encoding = "utf-8", name = "mysql-config.properties")
public class DataSourceConfig {

    /**
     * 连接到指定远程数据库标识符,数据库的地址。
     */
    @Value("${jdbc.url}")
    private String url;
    /**
     * 数据库用户名（用于连接数据库）
     */
    @Value("${jdbc.username}")
    private String username;
    /**
     * 用户密码（用于连接数据库）
     */
    @Value("${jdbc.password}")
    private String password;
    /**
     * 初始化连接大小,设0为没有限制
     */
    @Value("${jdbc.initialSize}")
    private Integer initialSize;
    /**
     * 连接池最大使用连接数量
     */
    @Value("${jdbc.maxActive}")
    private Integer maxActive;
    /**
     * 连接池最小空闲数量
     */
    @Value("${jdbc.minIdle}")
    private Integer minIdle;
    /**
     * 最大等待时间,单位是毫秒
     */
    @Value("${jdbc.maxWait}")
    private Integer maxWait;
    /**
     * 用来验证数据库连接的查询语句，这个查询语句必须是至少返回一条数据的SELECT语句
     */
    @Value("${jdbc.validationQuery}")
    private String validationQuery;
    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    @Value("${jdbc.testOnBorrow}")
    private Boolean testOnBorrow;
    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    @Value("${jdbc.testOnReturn}")
    private Boolean testOnReturn;
    /**
     * testWhileIdle建议配置为true，不影响性能，并且保证安全性
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
     */
    @Value("${jdbc.testWhileIdle}")
    private Boolean testWhileIdle;
    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("${jdbc.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("${jdbc.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    /**
     * 是否自动回收超时连接
     */
    @Value("${jdbc.removeAbandoned}")
    private Boolean removeAbandoned;
    /**
     * 超时时间(以秒数为单位)
     */
    @Value("${jdbc.removeAbandonedTimeout}")
    private Integer removeAbandonedTimeout;
    /**
     * 是否在自动回收超时连接的时候打印连接的超时错误
     */
    @Value("${jdbc.logAbandoned}")
    private Boolean logAbandoned;
    /**
     * 监控数据库,filters;mergeStat,log4j
     * Druid内置提供一个StatFilter(stat)，用于统计监控信息
     */
    @Value("${jdbc.filters}")
    private String filters;


    /**
     * 数据库连接池
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxWait(this.maxWait);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestOnBorrow(this.testOnBorrow);
        druidDataSource.setTestOnReturn(this.testOnReturn);
        druidDataSource.setTestWhileIdle(this.testWhileIdle);
        druidDataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        druidDataSource.setRemoveAbandoned(this.removeAbandoned);
        druidDataSource.setRemoveAbandonedTimeout(this.removeAbandonedTimeout);
        druidDataSource.setLogAbandoned(this.logAbandoned);
        return druidDataSource;
    }

}
