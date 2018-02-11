package com.itopener.zuul.ratelimiter.db.spring.boot.autoconfigure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.itopener.zuul.ratelimiter.spring.boot.common.entity.ZuulIdEntity;
import com.itopener.zuul.ratelimiter.spring.boot.common.entity.ZuulPathEntity;
import com.itopener.zuul.ratelimiter.spring.boot.common.support.ILimiterManager;

/**
 * @author fuwei.deng
 * @date 2017年6月30日 上午11:11:19
 * @version 1.0.0
 */
public class DatabaseLimiterManager implements ILimiterManager {

	public final static Logger logger = LoggerFactory.getLogger(DatabaseLimiterManager.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ZuulRateLimiterDatabaseProperties zuulRateLimiterDatabaseProperties;

	@Override
	public List<ZuulIdEntity> getZuulIds() {
		String sql = "select * from " + zuulRateLimiterDatabaseProperties.getIdLimiterTable() + " where enable = true";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ZuulIdEntity.class));
	}

	@Override
	public List<ZuulPathEntity> getZuulPaths() {
		String sql = "select * from " + zuulRateLimiterDatabaseProperties.getPathLimiterTable() + " where enable = true";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ZuulPathEntity.class));
	}

}