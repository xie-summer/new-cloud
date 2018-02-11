package com.itopener.zuul.route.spring.boot.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itopener.zuul.route.spring.boot.common.rule.IZuulRouteRule;

public class ZuulRouteRuleEntity implements IZuulRouteRule {
	
	/** */
	private static final long serialVersionUID = -8909855285961467412L;

	private final Logger logger = LoggerFactory.getLogger(ZuulRouteRuleEntity.class);

	/** 规则ID*/
	private String id;
	
	/** zuulRouteEntity id*/
	private String routeId;
	
	/** 规则*/
	private String rule;
	
	/** 期望结果*/
	private String expectedResult;
	
	/** 匹配后的路由目的地址*/
	private String location;
	
	/** 是否可用 */
	private boolean enable;
	
	/** 顺序，数字越小，优先级越高*/
	private int sortNum;
	
	@Override
	public boolean match(Map<String, Object> params) {
		if(expectedResult == null || rule == null){
			return false;
		}
		String ruleResult = RuleUtil.eval(rule, params);
		if(ruleResult == null){
			return false;
		}
		logger.info("规则执行结果:{}，期望结果:{}", ruleResult, expectedResult);
		return ruleResult.equals(expectedResult);
	}
	
	@Override
	public int getOrder() {
		return sortNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	
}
