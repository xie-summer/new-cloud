package com.itopener.zuul.route.spring.boot.common.rule;

import java.io.Serializable;
import java.util.Map;

import org.springframework.core.Ordered;

public interface IZuulRouteRule extends Ordered, Serializable{
	
	boolean match(Map<String, Object> params);
	
	String getLocation();
	
}
