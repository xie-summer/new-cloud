package com.itopener.zuul.route.spring.boot.common.rule;

import java.util.List;

import org.springframework.cloud.netflix.zuul.filters.Route;

public interface IZuulRouteRuleMatcher {

	public Route matchingRule(Route route, List<IZuulRouteRule> rules);
}
