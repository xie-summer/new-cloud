package com.cloud.framework.config;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;


/**
 * <p>
 * 统一配置文件管理服务
 * </p>
 *
 * @author summer
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
        DB_LOGGER.warn("ConfigApplication started successfully");
    }

    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(ConfigApplication.class);


}
