package com.cloud.doc;

import com.cloud.butler.configure.EnableSwaggerButler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/** @author xieshengrong */

@EnableDiscoveryClient
@EnableSwaggerButler
@SpringBootApplication
public class ConsulSwaggerDocApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulSwaggerDocApplication.class);
    }

}
