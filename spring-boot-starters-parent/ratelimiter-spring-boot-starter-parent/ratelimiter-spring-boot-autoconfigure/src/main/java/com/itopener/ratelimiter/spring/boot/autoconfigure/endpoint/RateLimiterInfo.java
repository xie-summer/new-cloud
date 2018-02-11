package com.itopener.ratelimiter.spring.boot.autoconfigure.endpoint;

public class RateLimiterInfo {

	private String name;
	
	private double rate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
}
