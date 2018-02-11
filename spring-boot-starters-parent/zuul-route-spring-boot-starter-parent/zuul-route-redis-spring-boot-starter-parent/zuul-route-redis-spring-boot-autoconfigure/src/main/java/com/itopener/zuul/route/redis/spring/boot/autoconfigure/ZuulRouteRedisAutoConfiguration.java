package com.itopener.zuul.route.redis.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.itopener.zuul.route.spring.boot.common.refresh.AutoRefreshRouteJob;
import com.itopener.zuul.route.spring.boot.common.refresh.RefreshRouteService;
import com.itopener.zuul.route.spring.boot.common.rule.DefaultZuulRouteRuleMatcher;
import com.itopener.zuul.route.spring.boot.common.rule.IZuulRouteRuleMatcher;

@Configuration
@EnableScheduling
@ConditionalOnBean(RedisTemplate.class)
@EnableConfigurationProperties(ZuulRouteRedisProperties.class)
public class ZuulRouteRedisAutoConfiguration {

	@Autowired
	ZuulProperties zuulProperties;

	@Autowired
	ServerProperties server;

	@Bean
	@ConditionalOnBean(RedisTemplate.class)
	@ConditionalOnMissingBean(ZuulRouteRedisLocator.class)
	public ZuulRouteRedisLocator zuulRedisRouteLocator() {
		return new ZuulRouteRedisLocator(this.server.getServletPrefix(), this.zuulProperties);
	}
	
	@Bean
	public RefreshRouteService refreshRouteService(){
		return new RefreshRouteService();
	}
	
	@Bean
	public AutoRefreshRouteJob autoRefreshRouteJob(){
		return new AutoRefreshRouteJob();
	}
	
	@Bean
	@ConditionalOnMissingBean(IZuulRouteRuleMatcher.class)
	public IZuulRouteRuleMatcher zuulRouteRuleMatcher(){
		return new DefaultZuulRouteRuleMatcher();
	}
}
