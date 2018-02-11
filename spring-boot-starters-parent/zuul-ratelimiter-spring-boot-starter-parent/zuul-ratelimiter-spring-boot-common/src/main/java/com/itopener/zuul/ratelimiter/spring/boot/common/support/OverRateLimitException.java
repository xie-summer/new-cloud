package com.itopener.zuul.ratelimiter.spring.boot.common.support;

import com.itopener.zuul.ratelimiter.spring.boot.common.entity.LimiterEntity;

/**
 * @description 超过限流时抛出的异常，自定义异常方便filter中处理
 * @author fuwei.deng
 * @date 2018年2月2日 下午3:33:33
 * @version 1.0.0
 */
public class OverRateLimitException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 3069590048328195077L;
	
	private int code;
	
	private LimiterEntity limiterEntity;
	
	public OverRateLimitException() {
		super();
	}

	public OverRateLimitException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public OverRateLimitException(int code, String message, LimiterEntity limiterEntity) {
		super(message);
		this.code = code;
		this.limiterEntity = limiterEntity;
	}

	public OverRateLimitException(String message) {
        super(message);
    }

    public OverRateLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverRateLimitException(Throwable cause) {
        super(cause);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public LimiterEntity getLimiterEntity() {
		return limiterEntity;
	}

	public void setLimiterEntity(LimiterEntity limiterEntity) {
		this.limiterEntity = limiterEntity;
	}
}
