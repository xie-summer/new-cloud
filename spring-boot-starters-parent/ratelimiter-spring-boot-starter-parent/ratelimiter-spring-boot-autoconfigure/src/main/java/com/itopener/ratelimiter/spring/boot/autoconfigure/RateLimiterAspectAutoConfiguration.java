package com.itopener.ratelimiter.spring.boot.autoconfigure;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import com.google.common.util.concurrent.RateLimiter;
import com.itopener.ratelimiter.spring.boot.autoconfigure.annotations.GuavaRateLimiter;
import com.itopener.ratelimiter.spring.boot.autoconfigure.support.RateLimiterHandler;

@Aspect
@Configuration
@ConditionalOnClass(RateLimiter.class)
public class RateLimiterAspectAutoConfiguration {

	private final Logger logger = LoggerFactory.getLogger(RateLimiterAspectAutoConfiguration.class);
	
	@Resource
	private RateLimiterHandler rateLimiterHandler;
	
	@Pointcut("@annotation(com.itopener.ratelimiter.spring.boot.autoconfigure.annotations.GuavaRateLimiter)")
	private void rateLimiterPoint(){
		
	}
	
	@Around("rateLimiterPoint()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		GuavaRateLimiter guavaRateLimiter = method.getAnnotation(GuavaRateLimiter.class);
		
		if(!rateLimiterHandler.tryAcquire(guavaRateLimiter)) {
			logger.warn("[{}] has over limit", guavaRateLimiter.value());
			if(guavaRateLimiter.exception() != null) {
				throw guavaRateLimiter.exception().getConstructor(String.class).newInstance("[{" + guavaRateLimiter.value() + "}] has over limit");
			}
			return null;
		}
		
		try {
			return pjp.proceed();
		} catch (Exception e) {
			logger.error("execute rate limiter method [" + guavaRateLimiter.value() + "] occured an exception", e);
		}
		return null;
	}
	
}
