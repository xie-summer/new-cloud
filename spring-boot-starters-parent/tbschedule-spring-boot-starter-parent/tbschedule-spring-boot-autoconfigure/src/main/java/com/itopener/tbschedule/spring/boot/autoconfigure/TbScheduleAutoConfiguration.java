package com.itopener.tbschedule.spring.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;

/**  
 * @author fuwei.deng
 * @Date 2017年6月9日 下午3:10:58
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(TbScheduleProperties.class)
public class TbScheduleAutoConfiguration {
	
	private final Logger logger = LoggerFactory.getLogger(TbScheduleAutoConfiguration.class);
	
	@Autowired
	private TbScheduleProperties tbScheduleProperties;
	
	@Bean(initMethod="init")
	public TBScheduleManagerFactory tbScheduleManagerFactory(){
		TBScheduleManagerFactory tbScheduleManagerFactory = new TBScheduleManagerFactory();
		tbScheduleManagerFactory.setZkConfig(tbScheduleProperties.getZkConfig());
		logger.info("tbschedule manager factory init success.");
		return tbScheduleManagerFactory;
	}
	
}
