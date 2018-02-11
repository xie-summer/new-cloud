package com.itopener.zuul.ratelimiter.spring.boot.common.entity;

/**
 * @description 使用zuul id配置限流
 * @author fuwei.deng
 * @date 2018年2月1日 下午3:49:33
 * @version 1.0.0
 */
public class ZuulIdEntity extends LimiterEntity {

	/** */
	private static final long serialVersionUID = 7026780267624790202L;
	
	/** zuul路由配置的id*/
	private String zuulId;

	public String getZuulId() {
		return zuulId;
	}

	public void setZuulId(String zuulId) {
		this.zuulId = zuulId;
	}
	
}
