package com.itopener.lock.redisson.spring.boot.autoconfigure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import org.springframework.core.annotation.AliasFor;

/**
 * @author fuwei.deng
 * @date 2017年6月14日 下午3:10:36
 * @version 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LockAction {

	/** 锁的资源，key。支持spring El表达式*/
	@AliasFor("key")
	String value() default "'default'";
	
	@AliasFor("value")
	String key() default "'default'";
	
	/** 锁类型*/
	LockType lockType() default LockType.REENTRANT_LOCK;
	
	/** 获取锁等待时间，默认3秒*/
	long waitTime() default 3000L;
	
	/** 锁自动释放时间，默认30秒*/
	long leaseTime() default 30000L;
	
	/** 时间单位（获取锁等待时间和持锁时间都用此单位）*/
	TimeUnit unit() default TimeUnit.MILLISECONDS;
}
