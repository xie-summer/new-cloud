package com.itopener.lock.redisson.spring.boot.autoconfigure;

/**  
 * @author fuwei.deng
 * @date 2018年1月9日 上午11:25:59
 * @version 1.0.0
 */
public enum LockType {

	/** 可重入锁*/
	REENTRANT_LOCK,
	
	/** 公平锁*/
	FAIR_LOCK,
	
	/** 读锁*/
	READ_LOCK,
	
	/** 写锁*/
	WRITE_LOCK;
}
