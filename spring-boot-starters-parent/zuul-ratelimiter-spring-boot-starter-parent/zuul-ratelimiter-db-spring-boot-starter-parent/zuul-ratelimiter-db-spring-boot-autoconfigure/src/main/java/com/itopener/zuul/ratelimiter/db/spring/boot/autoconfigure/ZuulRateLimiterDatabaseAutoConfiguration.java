package com.itopener.zuul.ratelimiter.db.spring.boot.autoconfigure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(ZuulRateLimiterDatabaseProperties.class)
public class ZuulRateLimiterDatabaseAutoConfiguration {

	@Autowired
	ZuulProperties zuulProperties;

	@Autowired
	DataSource dataSource;
	
	@Bean
	@ConditionalOnMissingBean(JdbcTemplate.class)
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource);
	}

	@Bean
	@ConditionalOnBean(JdbcTemplate.class)
	@ConditionalOnMissingBean(DatabaseLimiterManager.class)
	public DatabaseLimiterManager databaseLimiterManager() {
		return new DatabaseLimiterManager();
	}
}
