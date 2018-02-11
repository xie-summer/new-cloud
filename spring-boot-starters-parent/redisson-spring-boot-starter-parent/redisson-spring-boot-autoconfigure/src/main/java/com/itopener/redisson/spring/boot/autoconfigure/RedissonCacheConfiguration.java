package com.itopener.redisson.spring.boot.autoconfigure;

import java.util.Map;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * @author fuwei.deng
 * @date 2018年1月10日 下午3:05:52
 * @version 1.0.0
 */
@Configuration
@AutoConfigureAfter(RedissonAutoConfiguration.class)
@ConditionalOnBean(RedissonClient.class)
public class RedissonCacheConfiguration {

	@Autowired
	private RedissonProperties redissonProperties;

	@Bean
	@ConditionalOnMissingBean(CacheManager.class)
	public RedissonSpringCacheManager cacheManager(RedissonClient redissonClient) {
		RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient);

		Map<String, CacheConfig> cacheConfigs = redissonProperties.getCaches();
		if (!CollectionUtils.isEmpty(cacheConfigs)) {
			cacheManager.setConfig(cacheConfigs);
		}

		return cacheManager;
	}
}
