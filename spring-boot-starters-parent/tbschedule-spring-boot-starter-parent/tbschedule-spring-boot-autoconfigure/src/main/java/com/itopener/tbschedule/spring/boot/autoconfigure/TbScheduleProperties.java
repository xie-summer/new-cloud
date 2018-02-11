package com.itopener.tbschedule.spring.boot.autoconfigure;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**  
 * @author fuwei.deng
 * @Date 2017年6月9日 下午3:10:58
 * @version 1.0.0
 */
@ConfigurationProperties(prefix="spring.tbschedule")
public class TbScheduleProperties {

	private Map<String, String> zkConfig;
	
	public Map<String, String> getZkConfig() {
		return zkConfig;
	}

	public void setZkConfig(Map<String, String> zkConfig) {
		this.zkConfig = zkConfig;
	}
}
