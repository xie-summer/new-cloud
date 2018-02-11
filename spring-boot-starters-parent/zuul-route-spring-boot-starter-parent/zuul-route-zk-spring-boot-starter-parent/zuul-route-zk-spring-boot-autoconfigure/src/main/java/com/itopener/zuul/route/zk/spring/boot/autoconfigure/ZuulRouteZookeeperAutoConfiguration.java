package com.itopener.zuul.route.zk.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itopener.zuul.route.spring.boot.common.refresh.RefreshRouteService;
import com.itopener.zuul.route.spring.boot.common.rule.DefaultZuulRouteRuleMatcher;
import com.itopener.zuul.route.spring.boot.common.rule.IZuulRouteRuleMatcher;

@Configuration
@EnableConfigurationProperties(ZuulRouteZookeeperProperties.class)
public class ZuulRouteZookeeperAutoConfiguration {

	@Autowired
	ZuulProperties zuulProperties;

	@Autowired
	ServerProperties server;

	@Bean(initMethod = "init")
	public CuratorFrameworkClient curatorFrameworkClient(){
		return new CuratorFrameworkClient();
	}
	
	@Bean(initMethod = "init")
	public ZookeeperTreeCacheListener zookeeperTreeCacheListener(){
		return new ZookeeperTreeCacheListener();
	}

	@Bean
	@ConditionalOnMissingBean(ZuulRouteZookeeperLocator.class)
	public ZuulRouteZookeeperLocator zuulZookeeperRouteLocator() {
		return new ZuulRouteZookeeperLocator(this.server.getServletPrefix(), this.zuulProperties);
	}
	
	@Bean
	public RefreshRouteService refreshRouteService(){
		return new RefreshRouteService();
	}
	
//	@Bean
//	public AutoRefreshRouteJob autoRefreshRouteJob(){
//		return new AutoRefreshRouteJob();
//	}
	
	@Bean
	@ConditionalOnMissingBean(IZuulRouteRuleMatcher.class)
	public IZuulRouteRuleMatcher zuulRouteRuleMatcher(){
		return new DefaultZuulRouteRuleMatcher();
	}
}
