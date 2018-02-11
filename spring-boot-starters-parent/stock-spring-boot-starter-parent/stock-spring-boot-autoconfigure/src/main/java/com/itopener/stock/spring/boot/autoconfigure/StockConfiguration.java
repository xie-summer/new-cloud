package com.itopener.stock.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.itopener.stock.spring.boot.autoconfigure.support.Stock;

/**  
 * @author fuwei.deng
 * @date 2018年2月6日 下午6:22:19
 * @version 1.0.0
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class StockConfiguration {

	@Bean
	@ConditionalOnBean(RedisTemplate.class)
	public Stock stock(RedisTemplate<Object, Object> redisTemplate) {
		return new Stock(redisTemplate);
	}
}
