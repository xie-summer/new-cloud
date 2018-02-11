package com.itopener.lock.redisson.spring.boot.autoconfigure;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.itopener.redisson.spring.boot.autoconfigure.RedissonAutoConfiguration;

/**
 * @author fuwei.deng
 * @date 2017年6月14日 下午3:11:22
 * @version 1.0.0
 */
@Aspect
@Configuration
@ConditionalOnBean(RedissonClient.class)
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class RedissonDistributedLockAspectConfiguration {
	
	private final Logger logger = LoggerFactory.getLogger(RedissonDistributedLockAspectConfiguration.class);
	
	@Autowired
	private RedissonClient redissonClient;

	private ExpressionParser parser = new SpelExpressionParser();

	private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

	@Pointcut("@annotation(com.itopener.lock.redisson.spring.boot.autoconfigure.LockAction)")
	private void lockPoint(){
		
	}
	
	@Around("lockPoint()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		LockAction lockAction = method.getAnnotation(LockAction.class);
		String key = lockAction.value();
		Object[] args = pjp.getArgs();
		key = parse(key, method, args);
		
		RLock lock = getLock(key, lockAction);
		if(!lock.tryLock(lockAction.waitTime(), lockAction.leaseTime(), lockAction.unit())) {
			logger.debug("get lock failed [{}]", key);
			return null;
		}
		
		//得到锁,执行方法，释放锁
		logger.debug("get lock success [{}]", key);
		try {
			return pjp.proceed();
		} catch (Exception e) {
			logger.error("execute locked method occured an exception", e);
		} finally {
			lock.unlock();
			logger.debug("release lock [{}]", key);
		}
		return null;
	}

	/**
	 * @description 解析spring EL表达式
	 * @author fuwei.deng
	 * @date 2018年1月9日 上午10:41:01
	 * @version 1.0.0
	 * @param key 表达式
	 * @param method 方法
	 * @param args 方法参数
	 * @return
	 */
	private String parse(String key, Method method, Object[] args) {
		String[] params = discoverer.getParameterNames(method);
		EvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < params.length; i ++) {
			context.setVariable(params[i], args[i]);
		}
		return parser.parseExpression(key).getValue(context, String.class);
	}
	
	private RLock getLock(String key, LockAction lockAction) {
		switch (lockAction.lockType()) {
			case REENTRANT_LOCK:
				return redissonClient.getLock(key);
			
			case FAIR_LOCK:
				return redissonClient.getFairLock(key);
				
			case READ_LOCK:
				return redissonClient.getReadWriteLock(key).readLock();
			
			case WRITE_LOCK:
				return redissonClient.getReadWriteLock(key).writeLock();
				
			default:
				throw new RuntimeException("do not support lock type:" + lockAction.lockType().name());
			}
	}
}
