package com.invoke.configure;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * @author sunmer
 * <p>
 * 可以直接在yml中配置 @PropertySource(value = "classpath:db.properties",ignoreResourceNotFound = true,encoding = "utf-8")
 */
@Configuration
public class DruidDbConfig {
    private Logger logger = LoggerFactory.getLogger(DruidDbConfig.class);

    @Value("${dev.spring.datasource.url}")
    private String dbUrl;

    @Value("${dev.spring.datasource.username}")
    private String username;

    @Value("${dev.spring.datasource.password}")
    private String password;

    @Value("${dev.spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${dev.spring.datasource.initialSize}")
    private int initialSize;

    @Value("${dev.spring.datasource.minIdle}")
    private int minIdle;

    @Value("${dev.spring.datasource.maxActive}")
    private int maxActive;

    @Value("${dev.spring.datasource.maxWait}")
    private int maxWait;

    @Value("${dev.spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${dev.spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${dev.spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${dev.spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${dev.spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${dev.spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${dev.spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${dev.spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${dev.spring.datasource.filters}")
    private String filters;

    @Value("{dev.spring.datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * @return 声明其为Bean实例
     * 在同样的DataSource中，首先使用被标注的DataSource
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }
}
