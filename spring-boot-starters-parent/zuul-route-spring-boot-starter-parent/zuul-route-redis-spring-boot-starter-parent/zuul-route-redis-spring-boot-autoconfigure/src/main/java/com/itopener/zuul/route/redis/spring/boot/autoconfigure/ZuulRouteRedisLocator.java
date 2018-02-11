package com.itopener.zuul.route.redis.spring.boot.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import com.itopener.zuul.route.spring.boot.common.ZuulRouteEntity;
import com.itopener.zuul.route.spring.boot.common.ZuulRouteLocator;
import com.itopener.zuul.route.spring.boot.common.ZuulRouteRuleEntity;
import com.itopener.zuul.route.spring.boot.common.rule.IZuulRouteRule;

/**
 * @author fuwei.deng
 * @date 2017年6月30日 上午11:11:19
 * @version 1.0.0
 */
public class ZuulRouteRedisLocator extends ZuulRouteLocator {

	public final static Logger logger = LoggerFactory.getLogger(ZuulRouteRedisLocator.class);

	@Autowired
	private RedisTemplate<String, ZuulRouteEntity> redisTemplate;

	@Autowired
	private ZuulRouteRedisProperties zuulRouteRedisProperties;
	
	private List<ZuulRouteEntity> locateRouteList;

	public ZuulRouteRedisLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
	}

	@Override
	public Map<String, ZuulRoute> loadLocateRoute() {
		List<Object> redisResult = new ArrayList<Object>();
		locateRouteList = new ArrayList<ZuulRouteEntity>();
		try {
			redisResult = redisTemplate.opsForHash().values(zuulRouteRedisProperties.getNamespace());
			if (!CollectionUtils.isEmpty(redisResult)) {
				for (Object item : redisResult) {
					ZuulRouteEntity routeEntity = (ZuulRouteEntity) item;
					if(!routeEntity.isEnable()){
						continue;
					}
					List<Object> ruleResultList = redisTemplate.opsForHash().values(zuulRouteRedisProperties.getNamespace() + "_" + routeEntity.getId());
					routeEntity.setRuleList(new ArrayList<IZuulRouteRule>());
					if(!CollectionUtils.isEmpty(ruleResultList)){
						for(Object ruleItem : ruleResultList){
							ZuulRouteRuleEntity ruleEntity = (ZuulRouteRuleEntity) ruleItem;
							if(!ruleEntity.isEnable()){
								continue;
							}
							routeEntity.getRuleList().add(ruleEntity);
						}
					}
					locateRouteList.add(routeEntity);
				}
			}
		} catch (Exception e) {
			logger.error("load zuul route from redis exception", e);
		}
		return handle(locateRouteList);
	}

	@Override
	public List<IZuulRouteRule> getRules(Route route) {
		if(CollectionUtils.isEmpty(locateRouteList)){
			return null;
		}
		for(ZuulRouteEntity item : locateRouteList){
			if(item.getId().equals(route.getId())){
				return item.getRuleList();
			}
		}
		return null;
	}

}