package com.cloud;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
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
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(EurekaApplication.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
        DB_LOGGER.warn("EurekaApplication started successfully");
    }
}
