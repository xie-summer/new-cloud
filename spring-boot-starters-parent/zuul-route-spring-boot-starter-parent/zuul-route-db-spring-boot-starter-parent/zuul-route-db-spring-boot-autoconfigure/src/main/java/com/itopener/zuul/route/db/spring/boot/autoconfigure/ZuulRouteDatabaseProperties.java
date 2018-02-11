package com.itopener.zuul.route.db.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.zuul.route.db")
public class ZuulRouteDatabaseProperties {

	private String tableName = "zuul_route_config";
	
	private String ruleTableName = "zuul_route_config_rule";
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRuleTableName() {
		return ruleTableName;
	}

	public void setRuleTableName(String ruleTableName) {
		this.ruleTableName = ruleTableName;
	}
	
}
