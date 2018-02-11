package com.itopener.zuul.ratelimiter.spring.boot.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.util.concurrent.RateLimiter;

/**  
 * @author fuwei.deng
 * @date 2018年1月31日 下午5:47:55
 * @version 1.0.0
 */
@ConfigurationProperties(prefix="spring.zuul.ratelimiter")
public class ZuulRateLimiterProperties {

	/** 执行限流逻辑的总开关*/
	private boolean limiterSwitch = true;
	
	/** 超过限流配置后forward的路径，可以自定义此路径的controller对返回数据统一处理*/
	private String overLimitPath;
	
	/** 通过serviceId配置的限流。
	 * 由于path可能存在一些特殊字符，properties配置的方式无法兼容，所以暂不支持properties配置到path级别的限流
	 * 此处配置是properties中的配置，如果持久化配置了相同的限流配置，优先使用持久化的配置
	 * */
	private Map<String, Limiter> service = new HashMap<String, Limiter>();
	
	public class Limiter {

		/** 每秒限制数量*/
		private double permitsPerSecond = 80;
		
		/** 获取许可数量*/
		private int permits = 1;
		
		/** 获取许可超时时间*/
		private long timeout = 0;
		
		/** 获取许可超时时间单位*/
		private TimeUnit timeUnit = TimeUnit.MICROSECONDS;
		
		/** 超过限流值时抛出的异常*/
		private Class<? extends RuntimeException> exception;
		
		public RateLimiter getRateLimiter() {
			return RateLimiter.create(this.permitsPerSecond);
		}

		public double getPermitsPerSecond() {
			return permitsPerSecond;
		}

		public void setPermitsPerSecond(double permitsPerSecond) {
			this.permitsPerSecond = permitsPerSecond;
		}

		public int getPermits() {
			return permits;
		}

		public void setPermits(int permits) {
			this.permits = permits;
		}

		public long getTimeout() {
			return timeout;
		}

		public void setTimeout(long timeout) {
			this.timeout = timeout;
		}

		public TimeUnit getTimeUnit() {
			return timeUnit;
		}

		public void setTimeUnit(TimeUnit timeUnit) {
			this.timeUnit = timeUnit;
		}

		public Class<? extends RuntimeException> getException() {
			return exception;
		}

		public void setException(Class<? extends RuntimeException> exception) {
			this.exception = exception;
		}
	}

	public boolean isLimiterSwitch() {
		return limiterSwitch;
	}

	public void setLimiterSwitch(boolean limiterSwitch) {
		this.limiterSwitch = limiterSwitch;
	}

	public String getOverLimitPath() {
		return overLimitPath;
	}

	public void setOverLimitPath(String overLimitPath) {
		this.overLimitPath = overLimitPath;
	}

	public Map<String, Limiter> getService() {
		return service;
	}

	public void setService(Map<String, Limiter> service) {
		this.service = service;
	}
	
}
