package com.cloud.doc;

import com.cloud.butler.configure.EnableSwaggerButler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/** @author xieshengrong */
@EnableDiscoveryClient
@EnableSwaggerButler
@SpringBootApplication
@ComponentScan(basePackages = {"com.cloud.butler.configure"})
public class EurekaSwaggerDocApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaSwaggerDocApplication.class);
    }
}
