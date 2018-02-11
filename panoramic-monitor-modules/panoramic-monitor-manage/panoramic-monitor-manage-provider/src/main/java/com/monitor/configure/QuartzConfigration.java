package com.monitor.configure;

import com.monitor.model.task.MyJobFactory;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author summer
 */
//@Configuration
public class QuartzConfigration {

    @Autowired
    private MyJobFactory myJobFactory;
    @Value("${quartzConfig}")
    private String quartzConfig;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        try {
            schedulerFactoryBean.setQuartzProperties(quartzProperties());
            schedulerFactoryBean.setJobFactory(myJobFactory);
            // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            // 延时启动，应用启动1秒后
            schedulerFactoryBean.setStartupDelay(1);
        } catch (Exception e) {
        }
        return schedulerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(quartzConfig));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public Scheduler scheduler() {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        return scheduler;
    }
}