package com.itopener.ratelimiter.spring.boot.autoconfigure.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.util.concurrent.RateLimiter;
import com.itopener.ratelimiter.spring.boot.autoconfigure.support.RateLimiterHandler;

@ConfigurationProperties(prefix = "endpoints.limiter")
public class RateLimiterEndpoint extends AbstractEndpoint<List<RateLimiterInfo>> {
	
	private RateLimiterHandler rateLimiterHandler;

	public RateLimiterEndpoint(RateLimiterHandler rateLimiterHandler) {
		super("limiter", false);
		this.rateLimiterHandler = rateLimiterHandler;
	}

	@Override
	public List<RateLimiterInfo> invoke() {
		List<RateLimiterInfo> rateLimiterInfoList = new ArrayList<>();
		Map<String, RateLimiter> rateLimiterMap = rateLimiterHandler.getRateLimiterMap();
		if(CollectionUtils.isEmpty(rateLimiterMap)) {
			return rateLimiterInfoList;
		}
		for(Entry<String, RateLimiter> rateLimiter : rateLimiterMap.entrySet()) {
			RateLimiterInfo rateLimiterInfo = new RateLimiterInfo();
			rateLimiterInfo.setName(rateLimiter.getKey());
			rateLimiterInfo.setRate(rateLimiter.getValue().getRate());
			rateLimiterInfoList.add(rateLimiterInfo);
		}
		return rateLimiterInfoList;
	}

	public void set(String limiterName, double rate) {
		Map<String, RateLimiter> rateLimiterMap = rateLimiterHandler.getRateLimiterMap();
		Assert.notNull(rateLimiterMap, "the RateLimiterMap is empty:" + limiterName);
		RateLimiter rateLimiter = rateLimiterMap.get(limiterName);
		Assert.notNull(rateLimiter, "do not find the RateLimiter:" + limiterName);
		rateLimiter.setRate(rate);
	}
	
	public double get(String limiterName) {
		Map<String, RateLimiter> rateLimiterMap = rateLimiterHandler.getRateLimiterMap();
		Assert.notNull(rateLimiterMap, "the RateLimiterMap is empty:" + limiterName);
		RateLimiter rateLimiter = rateLimiterMap.get(limiterName);
		Assert.notNull(rateLimiter, "do not find the RateLimiter:" + limiterName);
		return rateLimiter.getRate();
	}
}
