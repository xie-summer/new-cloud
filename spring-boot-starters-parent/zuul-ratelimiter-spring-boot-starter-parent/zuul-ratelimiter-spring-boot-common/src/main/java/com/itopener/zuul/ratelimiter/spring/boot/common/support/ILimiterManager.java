package com.itopener.zuul.ratelimiter.spring.boot.common.support;

import java.util.List;

import com.itopener.zuul.ratelimiter.spring.boot.common.entity.ZuulIdEntity;
import com.itopener.zuul.ratelimiter.spring.boot.common.entity.ZuulPathEntity;

/**
 * @desctiption 限流配置管理的接口，由具体实现去决定使用什么存储方式
 * @author fuwei.deng
 * @date 2018年2月1日 下午3:45:08
 * @version 1.0.0
 */
public interface ILimiterManager {

	/**
	 * @description 获取zuul路由的id
	 * @author fuwei.deng
	 * @date 2018年2月1日 下午3:47:24
	 * @version 1.0.0
	 * @return
	 */
	List<ZuulIdEntity> getZuulIds();
	
	/**
	 * @description 根据zuul路由id获取path
	 * @author fuwei.deng
	 * @date 2018年2月1日 下午3:48:19
	 * @version 1.0.0
	 * @return
	 */
	List<ZuulPathEntity> getZuulPaths();
}
