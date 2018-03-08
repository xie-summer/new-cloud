package com.risk.warning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;

/**
 * @author sunmer
 * 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.cloud", "com.risk"})
public class RiskWarningApplication {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(RiskWarningApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(RiskWarningApplication.class, args);
		DB_LOGGER.warn("RiskWarningApplication started successfully");
	}

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
}
