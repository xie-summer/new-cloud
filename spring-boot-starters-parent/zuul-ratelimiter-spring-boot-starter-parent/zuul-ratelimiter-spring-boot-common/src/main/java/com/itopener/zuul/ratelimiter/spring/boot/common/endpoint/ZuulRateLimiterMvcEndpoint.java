package com.itopener.zuul.ratelimiter.spring.boot.common.endpoint;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.actuate.endpoint.mvc.HypermediaDisabled;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 使用path配置限流的endpoint，支持获取和更新限流配置
 * @author fuwei.deng
 * @date 2018年2月5日 上午9:47:46
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "endpoints.zuul.limiter")
public class ZuulRateLimiterMvcEndpoint extends EndpointMvcAdapter {
	
	private final ZuulRateLimiterEndpoint delegate;

	public ZuulRateLimiterMvcEndpoint(ZuulRateLimiterEndpoint delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@GetMapping("/refresh")
	@ResponseBody
	@HypermediaDisabled
	public Object refresh() {
		if (!this.delegate.isEnabled()) {
			// Shouldn't happen - MVC endpoint shouldn't be registered when delegate's disabled
			return getDisabledResponse();
		}
		try {
			this.delegate.refresh();
			return ResponseEntity.ok().body(this.delegate.invoke());
		}
		catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
}
