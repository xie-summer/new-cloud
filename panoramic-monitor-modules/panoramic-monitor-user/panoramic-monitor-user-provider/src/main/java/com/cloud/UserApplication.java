package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.ObjectPostProcessor;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;

/**
 * @author sunmer
 * 启动类
 * 在classpath 下 两种格式是相同的
 * @EnableEurekaClient 更依赖于eureka注册中心
 * @EnableDiscoveryClient 可以在eureka中心或者zk，consul.所以更通用
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude={SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.monitor","com.cloud" })
public class UserApplication {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(UserApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		DB_LOGGER.warn("UserApplication started successfully");
	}
	
    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
}
