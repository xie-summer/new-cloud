package com.weimob.mengdian.soa.kafka.annotation;

import java.lang.annotation.*;

/**
 * @Author chenwp
 * @Date 2017-06-29 16:04
 * @Company WeiMob
 * @Description
 */

@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaTopic {
    String topic() default "default";
}
