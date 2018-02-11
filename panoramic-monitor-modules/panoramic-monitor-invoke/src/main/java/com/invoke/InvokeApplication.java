package com.invoke;

import javax.servlet.DispatcherType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.invoke.web.filter.GateWayAuthenticationFilter;

/**
 * @author sunmer
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.cloud", "com.invoke" })
public class InvokeApplication {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(InvokeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InvokeApplication.class, args);
		DB_LOGGER.warn("InvokeApplication started successfully");
	}

	@Bean
	public FilterRegistrationBean gateWayAuthenticationFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new GateWayAuthenticationFilter());
		registration.addUrlPatterns("/*");
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.addInitParameter("params", "paramValues");
		registration.setName("GateWayAuthenticationFilter");
		registration.setOrder(1);
		return registration;
	}
}
