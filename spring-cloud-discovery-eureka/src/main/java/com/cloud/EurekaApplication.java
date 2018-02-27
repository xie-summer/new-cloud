package com.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 使用Eureka做服务发现。
 *
 * @author summer
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    private static final transient Logger DB_LOGGER = LoggerFactory.getLogger(EurekaApplication.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
        DB_LOGGER.warn("EurekaApplication started successfully");
    }
}
