package com.itopener.redisson.spring.boot.autoconfigure.config;

import java.util.Set;

/**  
 * @author fuwei.deng
 * @date 2018年1月5日 下午1:55:02
 * @version 1.0.0
 */
public class MasterSlaveServersConfig extends BaseMasterSlaveServersConfig {

	/**
     * Redis slave servers addresses
     */
    private Set<String> slaveAddresses;

    /**
     * Redis master server address
     */
    private String masterAddress;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

	public Set<String> getSlaveAddresses() {
		return slaveAddresses;
	}

	public void setSlaveAddresses(Set<String> slaveAddresses) {
		this.slaveAddresses = slaveAddresses;
	}

	public String getMasterAddress() {
		return masterAddress;
	}

	public void setMasterAddress(String masterAddress) {
		this.masterAddress = masterAddress;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
    
}
