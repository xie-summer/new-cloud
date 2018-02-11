package com.itopener.elasticjob.spring.boot.autoconfigure;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;

@Configuration
public class ElasticJobEventConfiguration {

	@Bean
	@ConditionalOnBean(DataSource.class)
	@ConditionalOnProperty(name = "spring.elasticjob.event.db.enable", havingValue = "true", matchIfMissing = false)
	public JobEventConfiguration jobEventRdbConfiguration(DataSource dataSource) {
		return new JobEventRdbConfiguration(dataSource);
	}
}
