package com.itopener.lock.redis.spring.boot.autoconfigure.lock;

/**
 * @author fuwei.deng
 * @date 2017年6月14日 下午3:11:05
 * @version 1.0.0
 */
public interface DistributedLock {
	
	public static final long TIMEOUT_MILLIS = 30000;
	
	public static final int RETRY_TIMES = Integer.MAX_VALUE;
	
	public static final long SLEEP_MILLIS = 500;

	public boolean lock(String key);
	
	public boolean lock(String key, int retryTimes);
	
	public boolean lock(String key, int retryTimes, long sleepMillis);
	
	public boolean lock(String key, long expire);
	
	public boolean lock(String key, long expire, int retryTimes);
	
	public boolean lock(String key, long expire, int retryTimes, long sleepMillis);
	
	public boolean releaseLock(String key);
}
