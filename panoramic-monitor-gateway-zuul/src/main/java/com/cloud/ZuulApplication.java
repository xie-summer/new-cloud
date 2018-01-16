package com.cloud;

import com.cloud.filter.ThrowExceptionFilter;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * API 服务网关
 *
 * @author summer
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(ZuulApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
        DB_LOGGER.warn("ZuulApplication started successfully");

        // @Bean
        // public AccessFilter accessFilter() {
        // return new AccessFilter();
        // }
        // @Bean
        // public PatternServiceRouteMapper serviceRouteMapper() {
        // return new PatternServiceRouteMapper(
        // "(?<name>^.+)-(?<version>v.+$)",
        // "${version}/${name}");
        // }
    }

    @Bean
    public ThrowExceptionFilter throwExceptionFilter() {
        return new ThrowExceptionFilter();
    }
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}
}