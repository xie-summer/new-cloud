package com.itopener.redisson.spring.boot.autoconfigure.config;

import java.util.List;

/**  
 * @author fuwei.deng
 * @date 2018年1月5日 下午1:56:32
 * @version 1.0.0
 */
public class ReplicatedServersConfig extends BaseMasterSlaveServersConfig {

	/**
     * Replication group node urls list
     */
    private List<String> nodeAddresses;

    /**
     * Replication group scan interval in milliseconds
     */
    private int scanInterval = 1000;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

	public List<String> getNodeAddresses() {
		return nodeAddresses;
	}

	public void setNodeAddresses(List<String> nodeAddresses) {
		this.nodeAddresses = nodeAddresses;
	}

	public int getScanInterval() {
		return scanInterval;
	}

	public void setScanInterval(int scanInterval) {
		this.scanInterval = scanInterval;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
    
}
