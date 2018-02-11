package com.itopener.zuul.route.db.spring.boot.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import com.itopener.zuul.route.spring.boot.common.ZuulRouteEntity;
import com.itopener.zuul.route.spring.boot.common.ZuulRouteLocator;
import com.itopener.zuul.route.spring.boot.common.ZuulRouteRuleEntity;
import com.itopener.zuul.route.spring.boot.common.rule.IZuulRouteRule;

/**
 * @author fuwei.deng
 * @date 2017年6月30日 上午11:11:19
 * @version 1.0.0
 */
public class ZuulRouteDatabaseLocator extends ZuulRouteLocator {

	public final static Logger logger = LoggerFactory.getLogger(ZuulRouteDatabaseLocator.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ZuulRouteDatabaseProperties zuulRouteDatabaseProperties;
	
	private List<ZuulRouteEntity> locateRouteList;

	public ZuulRouteDatabaseLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
	}

	@Override
	public Map<String, ZuulRoute> loadLocateRoute() {
		locateRouteList = new ArrayList<ZuulRouteEntity>();
		try {
			String sql = "select * from " + zuulRouteDatabaseProperties.getTableName() + " where enable = true";
			locateRouteList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ZuulRouteEntity.class));
			if(!CollectionUtils.isEmpty(locateRouteList)){
				for(ZuulRouteEntity zuulRoute : locateRouteList){
					sql = "select * from " + zuulRouteDatabaseProperties.getRuleTableName() + " where enable = true and route_id = ?";
					List<ZuulRouteRuleEntity> ruleList = jdbcTemplate.query(sql, new String[]{zuulRoute.getId()}, new BeanPropertyRowMapper<>(ZuulRouteRuleEntity.class));
					zuulRoute.setRuleList(new ArrayList<IZuulRouteRule>());
					if(!CollectionUtils.isEmpty(ruleList)){
						for(ZuulRouteRuleEntity rule : ruleList){
							zuulRoute.getRuleList().add(rule);
						}
					}
				}
			}
		} catch (DataAccessException e) {
			logger.error("load zuul route from db exception", e);
		}
		return handle(locateRouteList);
	}

	@Override
	public List<IZuulRouteRule> getRules(Route route) {
		if(CollectionUtils.isEmpty(locateRouteList)){
			return null;
		}
		for(ZuulRouteEntity item : locateRouteList){
			if(item.getId().equals(route.getId())){
				return item.getRuleList();
			}
		}
		return null;
	}

}