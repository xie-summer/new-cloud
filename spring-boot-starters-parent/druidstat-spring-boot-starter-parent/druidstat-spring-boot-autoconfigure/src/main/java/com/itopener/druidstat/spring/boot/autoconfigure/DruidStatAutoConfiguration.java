package com.itopener.druidstat.spring.boot.autoconfigure;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**  
 * @author fuwei.deng
 * @Date 2017年6月9日 下午3:10:58
 * @version 1.0.0
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@EnableConfigurationProperties(DruidStatProperties.class)
public class DruidStatAutoConfiguration {
	
	private final Logger logger = LoggerFactory.getLogger(DruidStatAutoConfiguration.class);
	
	@Autowired
	private DruidStatProperties druidStatProperties;
	
	@Bean
	public FilterRegistrationBean druidWebStatFilter(){
		logger.info("add druid web stat filter");
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		WebStatFilter webStatFilter = new WebStatFilter();
		webStatFilter.setProfileEnable(druidStatProperties.isProfileEnable());
		registrationBean.addInitParameter("exclusions", druidStatProperties.getExclusions());
		List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add(druidStatProperties.getUrl());
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setName(druidStatProperties.getFilterName());
		registrationBean.setFilter(webStatFilter);
        return registrationBean;
	}
	
	@Bean
    public ServletRegistrationBean servletRegistrationBean() {
		logger.info("add druid stat view servlet");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		StatViewServlet statViewServlet = new StatViewServlet();
		List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add(druidStatProperties.getUrl()); 
		servletRegistrationBean.setUrlMappings(urlPatterns);
		// IP白名单
		if(!StringUtils.isEmpty(druidStatProperties.getAllow())){
			servletRegistrationBean.addInitParameter("allow", druidStatProperties.getAllow());
		}
		// IP黑名单
		if(!StringUtils.isEmpty(druidStatProperties.getDeny())){
			servletRegistrationBean.addInitParameter("deny", druidStatProperties.getDeny());
		}
		// 用户名
		if(!StringUtils.isEmpty(druidStatProperties.getUsername())){
			servletRegistrationBean.addInitParameter("loginUsername", druidStatProperties.getUsername());
		}
		// 密码
		if(!StringUtils.isEmpty(druidStatProperties.getPassword())){
			servletRegistrationBean.addInitParameter("loginPassword", druidStatProperties.getPassword());
		}
		// 禁用HTML页面上的“Reset All”功能
		servletRegistrationBean.addInitParameter("resetEnable", druidStatProperties.getReset());
		servletRegistrationBean.setServlet(statViewServlet);
        return servletRegistrationBean;
    }
}
