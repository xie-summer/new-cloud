package com.cloud.zuul;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * API 服务网关
 *
 * @author summer
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ZuulApplication {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(ZuulApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
        DB_LOGGER.warn("ZuulApplication started successfully");

        // @Bean
        // public PatternServiceRouteMapper serviceRouteMapper() {
        // return new PatternServiceRouteMapper(
        // "(?<name>^.+)-(?<version>v.+$)",
        // "${version}/${name}");
        // }
    }


}