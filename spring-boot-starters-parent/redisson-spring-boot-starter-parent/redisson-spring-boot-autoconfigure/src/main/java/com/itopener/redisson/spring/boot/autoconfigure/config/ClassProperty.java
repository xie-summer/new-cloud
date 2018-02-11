package com.itopener.redisson.spring.boot.autoconfigure.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**  
 * @author fuwei.deng
 * @date 2018年1月5日 下午2:56:50
 * @version 1.0.0
 */
public class ClassProperty {

	@JsonProperty("class")
	private String className;

	public ClassProperty(String className) {
		super();
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
