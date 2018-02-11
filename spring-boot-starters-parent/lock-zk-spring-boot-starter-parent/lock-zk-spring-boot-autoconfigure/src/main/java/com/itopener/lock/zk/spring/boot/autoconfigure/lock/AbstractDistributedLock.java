package com.itopener.lock.zk.spring.boot.autoconfigure.lock;

/**  
 * @author fuwei.deng
 * @Date 2017年6月9日 下午3:10:58
 * @version 1.0.0
 */
public class AbstractDistributedLock implements DistributedLock {

	@Override
	public boolean lock(String key) {
		return lock(key, TIMEOUT_SECOND);
	}

	@Override
	public boolean lock(String key, long expire) {
		return false;
	}

	@Override
	public boolean releaseLock(String key) {
		return false;
	}

}
