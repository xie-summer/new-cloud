package com.itopener.zuul.ratelimiter.spring.boot.common.support;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.itopener.zuul.ratelimiter.spring.boot.common.ZuulRateLimiterProperties;
import com.itopener.zuul.ratelimiter.spring.boot.common.ZuulRateLimiterProperties.Limiter;
import com.itopener.zuul.ratelimiter.spring.boot.common.entity.LimiterEntity;
import com.itopener.zuul.ratelimiter.spring.boot.common.entity.ZuulIdEntity;
import com.itopener.zuul.ratelimiter.spring.boot.common.entity.ZuulPathEntity;

/**
 * @description 限流处理核心类
 * @author fuwei.deng
 * @date 2018年2月5日 上午9:51:01
 * @version 1.0.0
 */
public class RateLimiterHandler {
	
	private final Logger logger = LoggerFactory.getLogger(RateLimiterHandler.class);

	/** 通过serviceId配置的限流*/
	private Map<String, LimiterEntity> zuulIdRateLimiterMap = new ConcurrentHashMap<>();
	
	/** 通过path路径配置的限流，由于不同serviceId可能有相同路径，所以有两层Map，外层key是serviceId，内层key是path*/
	private Map<String, Map<String, LimiterEntity>> pathRateLimiterMap = new ConcurrentHashMap<>();
	
	private ZuulRateLimiterProperties zuulRateLimiterProperties;
	
	private ILimiterManager limiterManager;
	
	public RateLimiterHandler(ZuulRateLimiterProperties zuulRateLimiterProperties, ILimiterManager limiterManager) {
		super();
		this.zuulRateLimiterProperties = zuulRateLimiterProperties;
		this.limiterManager = limiterManager;
		generateRateLimiterMap();
	}

	/**
	 * @description 尝试获取令牌，限流的入口
	 * @author fuwei.deng
	 * @date 2018年2月1日 下午5:13:48
	 * @version 1.0.0
	 * @param route
	 * @return
	 */
	public void tryAcquire(Route route) {
		LimiterEntity limiter = getLimiterEntity(route);
		if(limiter == null) {
			return ;
		}
		
		if(limiter.rateLimiter().tryAcquire(limiter.getPermits(), limiter.getTimeout(), TimeUnit.valueOf(limiter.getTimeUnit()))) {
			return ;
		}
		
		logger.warn("[{}---{}] has over limit", route.getId(), route.getPath());
		
		// 超过限制的流量，不执行之后的处理(ZuulFilter-->runFilter())
		throw new OverRateLimitException(limiter.getStatusCode(), limiter.getErrorCause(), limiter);
	}
	
	/**
	 * @description 获取限流配置
	 * @author fuwei.deng
	 * @date 2018年2月5日 上午9:24:16
	 * @version 1.0.0
	 * @param route
	 * @return
	 */
	private LimiterEntity getLimiterEntity(Route route) {
		LimiterEntity limiter = null;
		Map<String, LimiterEntity> zuulIdPathRateLimiterMap =  pathRateLimiterMap.get(route.getId());
		if(!CollectionUtils.isEmpty(zuulIdPathRateLimiterMap)) {
			limiter = zuulIdPathRateLimiterMap.get(route.getPath());
		}
		if(limiter == null) {
			limiter = zuulIdRateLimiterMap.get(route.getId());
		}
		return limiter;
	}
	
	/**
	 * @description 根据配置生成限流配置的map
	 * @author fuwei.deng
	 * @date 2018年2月5日 上午9:24:27
	 * @version 1.0.0
	 */
	public void generateRateLimiterMap() {
		zuulIdRateLimiterMap.clear();
		pathRateLimiterMap.clear();
		Map<String, Limiter> limiterMap = zuulRateLimiterProperties.getService();
		handleLimiterMap(limiterMap);
		List<ZuulIdEntity> zuulIdEntities = limiterManager.getZuulIds();
		handleZuulIdEntities(zuulIdEntities);
		List<ZuulPathEntity> zuulPathEntities = limiterManager.getZuulPaths();
		handleZuulPathEntities(zuulPathEntities);
		logger.debug("zuulIdRateLimiterMap:{}", JSON.toJSONString(zuulIdRateLimiterMap));
		logger.debug("pathRateLimiterMap:{}", JSON.toJSONString(pathRateLimiterMap));
	}
	
	private void handleLimiterMap(Map<String, Limiter> limiterMap) {
		if(CollectionUtils.isEmpty(limiterMap)) {
			logger.debug("no RateLimiter configuration in properties");
			return ;
		}
		for(Map.Entry<String, Limiter> limiter : limiterMap.entrySet()) {
			zuulIdRateLimiterMap.put(limiter.getKey(), trans(limiter.getKey(), limiter.getValue()));
		}
	}
	
	private ZuulIdEntity trans(String zuulId, Limiter limiter) {
		ZuulIdEntity zuulIdEntity = new ZuulIdEntity();
		zuulIdEntity.setZuulId(zuulId);
		zuulIdEntity.setPermits(limiter.getPermits());
		zuulIdEntity.setPermitsPerSecond(limiter.getPermitsPerSecond());
		zuulIdEntity.setTimeout(limiter.getTimeout());
		zuulIdEntity.setTimeUnit(limiter.getTimeUnit().name());
		return zuulIdEntity;
	}
	
	private void handleZuulIdEntities(List<ZuulIdEntity> zuulIdEntities) {
		if(CollectionUtils.isEmpty(zuulIdEntities)) {
			logger.debug("no zuul id RateLimiter configuration in db");
			return ;
		}
		for(ZuulIdEntity zuulIdEntity : zuulIdEntities) {
			zuulIdRateLimiterMap.put(zuulIdEntity.getZuulId(), zuulIdEntity);
		}
	}
	
	private void handleZuulPathEntities(List<ZuulPathEntity> zuulPathEntities) {
		if(CollectionUtils.isEmpty(zuulPathEntities)) {
			logger.debug("no zuul path RateLimiter configuration in db");
			return ;
		}
		for(ZuulPathEntity zuulPathEntity : zuulPathEntities) {
			Map<String, LimiterEntity> limiterEntities = pathRateLimiterMap.get(zuulPathEntity.getZuulId());
			if(CollectionUtils.isEmpty(limiterEntities)) {
				limiterEntities = new ConcurrentHashMap<>();
				pathRateLimiterMap.put(zuulPathEntity.getZuulId(), limiterEntities);
			}
			limiterEntities.put(zuulPathEntity.getPath(), zuulPathEntity);
		}
	}

	public Map<String, LimiterEntity> getZuulIdRateLimiterMap() {
		return zuulIdRateLimiterMap;
	}

	public Map<String, Map<String, LimiterEntity>> getPathRateLimiterMap() {
		return pathRateLimiterMap;
	}
	
	public void put(ZuulIdEntity zuulIdEntity) {
		zuulIdRateLimiterMap.put(zuulIdEntity.getZuulId(), zuulIdEntity);
	}
	
	public void put(ZuulPathEntity zuulPathEntity) {
		Map<String, LimiterEntity> limiterEntities = pathRateLimiterMap.get(zuulPathEntity.getZuulId());
		if(CollectionUtils.isEmpty(limiterEntities)) {
			limiterEntities = new ConcurrentHashMap<>();
			pathRateLimiterMap.put(zuulPathEntity.getZuulId(), limiterEntities);
		}
		limiterEntities.put(zuulPathEntity.getPath(), zuulPathEntity);
	}
}
