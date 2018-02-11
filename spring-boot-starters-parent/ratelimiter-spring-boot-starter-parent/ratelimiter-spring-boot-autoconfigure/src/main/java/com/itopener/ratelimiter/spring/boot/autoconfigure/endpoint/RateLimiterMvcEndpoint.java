package com.itopener.ratelimiter.spring.boot.autoconfigure.endpoint;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.actuate.endpoint.mvc.HypermediaDisabled;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ConfigurationProperties(prefix = "endpoints.limiter")
public class RateLimiterMvcEndpoint extends EndpointMvcAdapter {
	
	private final RateLimiterEndpoint delegate;

	public RateLimiterMvcEndpoint(RateLimiterEndpoint delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@PutMapping("/{name:.*}")
	@ResponseBody
	@HypermediaDisabled
	public Object set(@PathVariable String name, double rate) {
		if (!this.delegate.isEnabled()) {
			// Shouldn't happen - MVC endpoint shouldn't be registered when delegate's
			// disabled
			return getDisabledResponse();
		}
		try {
			this.delegate.set(name, rate);
			return ResponseEntity.ok().build();
		}
		catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@GetMapping("/{name:.*}")
	@ResponseBody
	@HypermediaDisabled
	public Object get(@PathVariable String name) {
		if (!this.delegate.isEnabled()) {
			// Shouldn't happen - MVC endpoint shouldn't be registered when delegate's
			// disabled
			return getDisabledResponse();
		}
		try {
			double rate = this.delegate.get(name);
			return ResponseEntity.ok().body(rate);
		}
		catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
}
