package com.cloud.configure;

import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author summer
 */
@Configuration
public class FeignConfiguration {
	@Bean
	public Contract feignContract() {
		return new feign.Contract.Default();
	}

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor("user", "user");
	}

	@Bean
	Request.Options feignOptions() {
		/** connectTimeoutMillis **/
		/** readTimeoutMillis **/
		return new Request.Options(
				1 * 100000,
				1 * 100000);
	}

	@Bean
	Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		// 设置日志
		return Logger.Level.FULL;
	}
}
