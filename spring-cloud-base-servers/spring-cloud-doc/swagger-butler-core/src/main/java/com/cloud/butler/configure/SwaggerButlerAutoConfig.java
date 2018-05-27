package com.cloud.butler.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xieshengrong
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerButlerProperties.class)
public class SwaggerButlerAutoConfig {

    @Bean
    @Primary
    public SwaggerResourcesProcessor swaggerResourcesProcessor() {
        return new SwaggerResourcesProcessor();
    }

}
