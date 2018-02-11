package com.itopener.lock.redis.spring.boot.autoconfigure.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

/**
 * @author fuwei.deng
 * @date 2017年6月14日 下午3:11:14
 * @version 1.0.0
 */
public class RedisDistributedLock extends AbstractDistributedLock {
	
	private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);
	
	private RedisTemplate<Object, Object> redisTemplate;
	
	private ThreadLocal<String> lockFlag = new ThreadLocal<String>();
	
	public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

	public RedisDistributedLock(RedisTemplate<Object, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@Override
	public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
		boolean result = setRedis(key, expire);
		// 如果获取锁失败，按照传入的重试次数进行重试
		while((!result) && retryTimes-- > 0){
			try {
				logger.debug("lock failed, retrying..." + retryTimes);
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e) {
				return false;
			}
			result = setRedis(key, expire);
		}
		return result;
	}
	
	private boolean setRedis(String key, long expire) {
		try {
			String result = redisTemplate.execute(new RedisCallback<String>() {
				@Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					JedisCommands commands = (JedisCommands) connection.getNativeConnection();
					String uuid = UUID.randomUUID().toString();
					lockFlag.set(uuid);
					return commands.set(key, uuid, "NX", "PX", expire);
				}
			});
			return !StringUtils.isEmpty(result);
		} catch (Exception e) {
			logger.error("set redis occured an exception", e);
		}
		return false;
	}
	
	@Override
	public boolean releaseLock(String key) {
		// 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
		try {
			List<String> keys = new ArrayList<String>();
			keys.add(key);
			List<String> args = new ArrayList<String>();
			args.add(lockFlag.get());

			// 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
			// spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
			
			Long result = redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					Object nativeConnection = connection.getNativeConnection();
					// 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
					// 集群模式
					if (nativeConnection instanceof JedisCluster) {
						return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
					}

					// 单机模式
					else if (nativeConnection instanceof Jedis) {
						return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
					}
					return 0L;
				}
			});
			
			return result != null && result > 0;
		} catch (Exception e) {
			logger.error("release lock occured an exception", e);
		}
		return false;
	}
	
}
