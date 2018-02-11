package com.itopener.ratelimiter.spring.boot.autoconfigure.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author fuwei.deng
 * @date 2017年6月14日 下午3:10:36
 * @version 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GuavaRateLimiter {

	/** 限流的key*/
	String value() default "default";
	
	/** 每秒限制数量*/
	double permitsPerSecond() default 80;
	
	/** 获取许可数量*/
	int permits() default 1;
	
	/** 获取许可超时时间*/
	long timeout() default 0;
	
	/** 获取许可超时时间单位*/
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
	
	/** 超过限流值时抛出的异常*/
	Class<? extends RuntimeException> exception();
}
