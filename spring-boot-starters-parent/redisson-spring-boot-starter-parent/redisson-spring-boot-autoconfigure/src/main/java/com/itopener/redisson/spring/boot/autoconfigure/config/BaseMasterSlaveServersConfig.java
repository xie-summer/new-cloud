package com.itopener.redisson.spring.boot.autoconfigure.config;

import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;

/**  
 * @author fuwei.deng
 * @date 2018年1月5日 下午1:50:57
 * @version 1.0.0
 */
public class BaseMasterSlaveServersConfig extends BaseConfig {

	/**
     * Сonnection load balancer for multiple Redis slave servers
     */
    private ClassProperty loadBalancer = new ClassProperty(RoundRobinLoadBalancer.class.getName());

    /**
     * Redis 'slave' node minimum idle connection amount for <b>each</b> slave node
     */
    private int slaveConnectionMinimumIdleSize = 10;

    /**
     * Redis 'slave' node maximum connection pool size for <b>each</b> slave node
     */
    private int slaveConnectionPoolSize = 64;

    /**
     * Redis 'master' node minimum idle connection amount for <b>each</b> slave node
     */
    private int masterConnectionMinimumIdleSize = 10;

    /**
     * Redis 'master' node maximum connection pool size
     */
    private int masterConnectionPoolSize = 64;

    private ReadMode readMode = ReadMode.SLAVE;
    
    private SubscriptionMode subscriptionMode = SubscriptionMode.SLAVE;
    
    /**
     * Redis 'slave' node minimum idle subscription (pub/sub) connection amount for <b>each</b> slave node
     */
    private int subscriptionConnectionMinimumIdleSize = 1;

    /**
     * Redis 'slave' node maximum subscription (pub/sub) connection pool size for <b>each</b> slave node
     */
    private int subscriptionConnectionPoolSize = 50;

    private long dnsMonitoringInterval = 5000;

	public ClassProperty getLoadBalancer() {
		return loadBalancer;
	}

	public void setLoadBalancer(ClassProperty loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	public int getSlaveConnectionMinimumIdleSize() {
		return slaveConnectionMinimumIdleSize;
	}

	public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
		this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
	}

	public int getSlaveConnectionPoolSize() {
		return slaveConnectionPoolSize;
	}

	public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
		this.slaveConnectionPoolSize = slaveConnectionPoolSize;
	}

	public int getMasterConnectionMinimumIdleSize() {
		return masterConnectionMinimumIdleSize;
	}

	public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
		this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
	}

	public int getMasterConnectionPoolSize() {
		return masterConnectionPoolSize;
	}

	public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
		this.masterConnectionPoolSize = masterConnectionPoolSize;
	}

	public ReadMode getReadMode() {
		return readMode;
	}

	public void setReadMode(ReadMode readMode) {
		this.readMode = readMode;
	}

	public SubscriptionMode getSubscriptionMode() {
		return subscriptionMode;
	}

	public void setSubscriptionMode(SubscriptionMode subscriptionMode) {
		this.subscriptionMode = subscriptionMode;
	}

	public int getSubscriptionConnectionMinimumIdleSize() {
		return subscriptionConnectionMinimumIdleSize;
	}

	public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
		this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
	}

	public int getSubscriptionConnectionPoolSize() {
		return subscriptionConnectionPoolSize;
	}

	public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
		this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
	}

	public long getDnsMonitoringInterval() {
		return dnsMonitoringInterval;
	}

	public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
		this.dnsMonitoringInterval = dnsMonitoringInterval;
	}
    
}
