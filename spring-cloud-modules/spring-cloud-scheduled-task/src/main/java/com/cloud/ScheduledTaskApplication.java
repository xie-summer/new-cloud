package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import com.cloud.service.feign.manage.ManageFeignHystrixClient.HystrixClientFallback;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;

/**
 * @author sunmer
 * 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@ComponentScan(basePackages = {"com.cloud"})
public class ScheduledTaskApplication {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(ScheduledTaskApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ScheduledTaskApplication.class, args);
        DB_LOGGER.warn("ScheduledTaskApplication started successfully");
    }
}
