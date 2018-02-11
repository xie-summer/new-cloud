package com.itopener.zuul.ratelimiter.db.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.zuul.ratelimiter.db")
public class ZuulRateLimiterDatabaseProperties {

	private String idLimiterTable = "zuul_id_limiter";
	
	private String pathLimiterTable = "zuul_path_limiter";

	public String getIdLimiterTable() {
		return idLimiterTable;
	}

	public void setIdLimiterTable(String idLimiterTable) {
		this.idLimiterTable = idLimiterTable;
	}

	public String getPathLimiterTable() {
		return pathLimiterTable;
	}

	public void setPathLimiterTable(String pathLimiterTable) {
		this.pathLimiterTable = pathLimiterTable;
	}

}
