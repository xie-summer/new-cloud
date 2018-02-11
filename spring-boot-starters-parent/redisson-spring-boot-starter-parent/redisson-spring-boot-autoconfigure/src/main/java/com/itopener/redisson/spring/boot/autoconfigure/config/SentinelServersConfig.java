package com.itopener.redisson.spring.boot.autoconfigure.config;

import java.util.List;

/**  
 * @author fuwei.deng
 * @date 2018年1月5日 下午1:57:41
 * @version 1.0.0
 */
public class SentinelServersConfig extends BaseMasterSlaveServersConfig {

	private List<String> sentinelAddresses;

    private String masterName;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

	public List<String> getSentinelAddresses() {
		return sentinelAddresses;
	}

	public void setSentinelAddresses(List<String> sentinelAddresses) {
		this.sentinelAddresses = sentinelAddresses;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
    
}
