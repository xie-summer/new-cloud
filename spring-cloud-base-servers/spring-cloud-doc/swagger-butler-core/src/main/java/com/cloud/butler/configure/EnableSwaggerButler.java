package com.cloud.butler.configure;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xieshengrong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SwaggerButlerAutoConfig.class})
@EnableZuulProxy
public @interface EnableSwaggerButler {
}