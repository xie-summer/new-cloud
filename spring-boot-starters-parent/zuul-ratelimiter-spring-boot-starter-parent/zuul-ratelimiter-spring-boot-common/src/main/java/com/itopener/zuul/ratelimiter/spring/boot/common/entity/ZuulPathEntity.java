package com.itopener.zuul.ratelimiter.spring.boot.common.entity;

/**
 * @description 使用访问路径配置限流
 * @author fuwei.deng
 * @date 2018年2月1日 下午3:49:33
 * @version 1.0.0
 */
public class ZuulPathEntity extends LimiterEntity {

	/** */
	private static final long serialVersionUID = 7026780267624790202L;
	
	/** 所属的zuul路由配置的id*/
	private String zuulId;
	
	/** 访问路径，是指访问的完整路径，如果使用RESTful风格的访问路径，则限流没有太大实际用处。<br/>
	 * 如果需完善，有两种建议方式：<br/>
	 * 1. 由应用内提供接口获取原始访问路径，在zuul filter中进行匹配<br/>
	 * 2. 或者直接在应用内使用ratelimiter-spring-boot-starter来限流<br/>
	 */
	private String path;
	
	public String getZuulId() {
		return zuulId;
	}

	public void setZuulId(String zuulId) {
		this.zuulId = zuulId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
