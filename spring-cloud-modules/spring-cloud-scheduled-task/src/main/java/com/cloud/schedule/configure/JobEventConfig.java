package com.cloud.schedule.configure;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/** @author summer 2018/6/1 */

@Configuration
public class JobEventConfig {

  @Resource private DataSource dataSource;

  @Bean
  public JobEventConfiguration jobEventConfiguration() {
    return new JobEventRdbConfiguration(dataSource);
  }
}
