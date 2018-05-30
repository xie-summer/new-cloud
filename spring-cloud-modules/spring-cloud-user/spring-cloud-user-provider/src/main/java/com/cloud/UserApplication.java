package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author sunmer 启动类 在classpath 下 两种格式是相同的 @EnableEurekaClient
 *     更依赖于eureka注册中心 @EnableDiscoveryClient 可以在eureka中心或者zk，consul.所以更通用
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = {"com.monitor.mapper"})
public class UserApplication {
  private static final transient Logger DB_LOGGER = LoggerFactory.getLogger(UserApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
    DB_LOGGER.warn("UserApplication started successfully");
  }

  //    @Bean
  //    public AuthenticationEventPublisher authenticationEventPublisher() {
  //        return new DefaultAuthenticationEventPublisher();
  //    }
}
