package com.itopener.zuul.ratelimiter.spring.boot.common.entity;

import java.io.Serializable;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @description 限流配置公共属性类
 * @author fuwei.deng
 * @date 2018年2月1日 下午3:51:57
 * @version 1.0.0
 */
public class LimiterEntity implements Serializable {

	/** */
	private static final long serialVersionUID = -2507493013440995379L;
	
	private long id;

	/** 每秒限制数量*/
	private double permitsPerSecond;
	
	/** 获取许可数量*/
	private int permits;
	
	/** 获取许可超时时间*/
	private long timeout;
	
	/** 获取许可超时时间单位*/
	private String timeUnit;
	
	/** 超过限流时的错误码*/
	private int statusCode;
	
	/** 超过限流时的错误原因*/
	private String errorCause;
	
	/** 是否启用*/
	private boolean enable;
	
	private RateLimiter rateLimiter;
	
	/**
	 * @description 获取RateLimiter限流对象
	 * @author fuwei.deng
	 * @date 2018年2月1日 下午5:08:29
	 * @version 1.0.0
	 * @return
	 */
	public RateLimiter rateLimiter() {
		if(this.rateLimiter != null) {
			return rateLimiter;
		}
		synchronized (this) {
			if(this.rateLimiter != null) {
				return rateLimiter;
			}
			this.rateLimiter = RateLimiter.create(this.permitsPerSecond);
			return rateLimiter;
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
