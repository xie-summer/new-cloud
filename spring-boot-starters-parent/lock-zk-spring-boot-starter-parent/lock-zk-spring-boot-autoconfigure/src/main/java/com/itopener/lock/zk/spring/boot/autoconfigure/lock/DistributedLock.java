package com.itopener.lock.zk.spring.boot.autoconfigure.lock;

/**  
 * @author fuwei.deng
 * @Date 2017年6月9日 下午3:10:58
 * @version 1.0.0
 */
public interface DistributedLock {
	
	public static final long TIMEOUT_SECOND = 30;
	
	public boolean lock(String key);
	
	public boolean lock(String key, long expire);
	
	public boolean releaseLock(String key);
}
