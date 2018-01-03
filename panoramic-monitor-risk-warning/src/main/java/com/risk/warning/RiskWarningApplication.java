package com.risk.warning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;

/**
 * @author sunmer
 * 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.cloud", "com.risk"})
public class RiskWarningApplication {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(RiskWarningApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RiskWarningApplication.class, args);
		DB_LOGGER.warn("RiskWarningApplication started successfully");
	}
}
