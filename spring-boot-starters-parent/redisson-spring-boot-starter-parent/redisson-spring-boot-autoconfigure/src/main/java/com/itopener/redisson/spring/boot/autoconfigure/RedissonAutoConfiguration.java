package com.itopener.redisson.spring.boot.autoconfigure;

import java.io.File;
import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author fuwei.deng
 * @date 2018年1月3日 下午2:13:48
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {
	
	private final Logger logger = LoggerFactory.getLogger(RedissonAutoConfiguration.class);
	
	@Autowired
	private RedissonProperties redissonProperties;

	public Config configJson() throws IOException {
		File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getJson());
		return Config.fromJSON(file);
	}
	
	public Config configYaml() throws IOException {
		File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getYaml());
		return Config.fromYAML(file);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public Config config() throws IOException {
		if (!StringUtils.isEmpty(redissonProperties.getConfigFile().getJson())) {
			return configJson();
		} else if (!StringUtils.isEmpty(redissonProperties.getConfigFile().getYaml())) {
			return configYaml();
		} else {
			ObjectMapper mapper = new ObjectMapper();
			String configJson = mapper.writeValueAsString(redissonProperties.getConfig());
			logger.info("config is : {}", configJson);
			return Config.fromJSON(configJson);
		}
	}
	
	@Bean(destroyMethod="shutdown")
	@ConditionalOnMissingBean
	public RedissonClient redissonClient(Config config) throws IOException {
		logger.info("create RedissonClient, config is : {}", config.toJSON());
		return Redisson.create(config);
	}
	
}
