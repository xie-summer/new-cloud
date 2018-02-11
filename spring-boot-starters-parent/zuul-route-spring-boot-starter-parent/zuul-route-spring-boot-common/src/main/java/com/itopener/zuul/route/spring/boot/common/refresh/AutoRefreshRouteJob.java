package com.itopener.zuul.route.spring.boot.common.refresh;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class AutoRefreshRouteJob {
	
	private final Logger logger = LoggerFactory.getLogger(AutoRefreshRouteJob.class);
	
	@Resource
	private RefreshRouteService refreshRouteService;

	@Scheduled(cron = "${spring.zuul.route.refreshCron:0/30 * * * * ?}")
	public void run() {
		logger.info("refresh zuul route config");
		refreshRouteService.refreshRoute();
	}

}
