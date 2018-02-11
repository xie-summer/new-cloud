package com.itopener.redisson.spring.boot.autoconfigure;

import java.util.Map;

import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.itopener.redisson.spring.boot.autoconfigure.config.Config;
import com.itopener.redisson.spring.boot.autoconfigure.config.ConfigFile;

/**
 * @author fuwei.deng
 * @date 2018年1月3日 下午2:12:47
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

	/** 属性的配置方式 */
	private Config config;

	/** 文件的配置方式 */
	private ConfigFile configFile = new ConfigFile();

	/** 方法注解缓存的配置 */
	private Map<String, CacheConfig> caches;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public ConfigFile getConfigFile() {
		return configFile;
	}

	public void setConfigFile(ConfigFile configFile) {
		this.configFile = configFile;
	}

	public Map<String, CacheConfig> getCaches() {
		return caches;
	}

	public void setCaches(Map<String, CacheConfig> caches) {
		this.caches = caches;
	}

}
