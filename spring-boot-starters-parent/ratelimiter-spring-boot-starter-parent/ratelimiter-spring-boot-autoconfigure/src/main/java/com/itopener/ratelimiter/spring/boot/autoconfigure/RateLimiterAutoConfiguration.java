package com.itopener.ratelimiter.spring.boot.autoconfigure;

import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.util.concurrent.RateLimiter;
import com.itopener.ratelimiter.spring.boot.autoconfigure.endpoint.RateLimiterEndpoint;
import com.itopener.ratelimiter.spring.boot.autoconfigure.endpoint.RateLimiterMvcEndpoint;
import com.itopener.ratelimiter.spring.boot.autoconfigure.support.RateLimiterHandler;

@Configuration
@ConditionalOnClass(RateLimiter.class)
public class RateLimiterAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public RateLimiterHandler rateLimiterHandler() {
		return new RateLimiterHandler();
	}
	
//	@Bean
//	@ConditionalOnMissingBean
//	public RateLimiterEndpoint rateLimiterEndpoint(RateLimiterHandler rateLimiterHandler) {
//		return new RateLimiterEndpoint(rateLimiterHandler);
//	}
	
//	@Bean
//	@ConditionalOnMissingBean
//	@ConditionalOnEnabledEndpoint("limiter")
//	public RateLimiterMvcEndpoint rateLimiterMvcEndpoint(RateLimiterEndpoint rateLimiterEndpoint) {
//		return new RateLimiterMvcEndpoint(rateLimiterEndpoint);
//	}
	
	/**
	 * @description 注册limiter的endpoint，RateLimiterMvcEndpoint中有RateLimiterEndpoint对象属性
	 * 		默认也会有invoke方法，调用的也是RateLimiterEndpoint的invoke方法，所以可以只注册这一个bean
	 * @author fuwei.deng
	 * @date 2018年2月11日 下午1:39:04
	 * @version 1.0.0
	 * @param rateLimiterHandler
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint("limiter")
	public RateLimiterMvcEndpoint rateLimiterMvcEndpoint(RateLimiterHandler rateLimiterHandler) {
		return new RateLimiterMvcEndpoint(new RateLimiterEndpoint(rateLimiterHandler));
	}
}
