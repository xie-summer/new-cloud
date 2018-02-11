package com.itopener.redisson.spring.boot.autoconfigure.config;

/**  
 * @author fuwei.deng
 * @date 2018年1月5日 下午2:01:13
 * @version 1.0.0
 */
public class SingleServerConfig extends BaseConfig {

	/**
     * Redis server address
     *
     */
    private String address;

    /**
     * Minimum idle subscription connection amount
     */
    private int subscriptionConnectionMinimumIdleSize = 1;

    /**
     * Redis subscription connection maximum pool size
     *
     */
    private int subscriptionConnectionPoolSize = 50;

    /**
     * Minimum idle Redis connection amount
     */
    private int connectionMinimumIdleSize = 10;

    /**
     * Redis connection maximum pool size
     */
    private int connectionPoolSize = 64;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

    /**
     * Should the server address be monitored for changes in DNS? Useful for
     * AWS ElastiCache where the client is pointed at the endpoint for a replication group
     * which is a DNS alias to the current master node.<br>
     * <em>NB: applications must ensure the JVM DNS cache TTL is low enough to support this.</em>
     * e.g., http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-jvm-ttl.html
     */
    private boolean dnsMonitoring = true;

    /**
     * Interval in milliseconds to check DNS
     */
    private long dnsMonitoringInterval = 5000;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getConnectionMinimumIdleSize() {
		return connectionMinimumIdleSize;
	}

	public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
		this.connectionMinimumIdleSize = connectionMinimumIdleSize;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public boolean isDnsMonitoring() {
		return dnsMonitoring;
	}

	public void setDnsMonitoring(boolean dnsMonitoring) {
		this.dnsMonitoring = dnsMonitoring;
	}

	public long getDnsMonitoringInterval() {
		return dnsMonitoringInterval;
	}

	public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
		this.dnsMonitoringInterval = dnsMonitoringInterval;
	}
    
}
