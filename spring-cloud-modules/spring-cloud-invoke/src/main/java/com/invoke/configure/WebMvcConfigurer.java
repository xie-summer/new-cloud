package com.invoke.configure;//package com.cloud.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.cloud.api.vo.ResultCode;
import com.cloud.constant.IpTypeConstants;
import com.cloud.constant.MarkConstant;
import com.cloud.constant.ProfilesConstant;
import com.cloud.core.ServiceException;
import com.invoke.web.interceptor.GateWayAuthenticationInterceptor;
import com.invoke.web.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author sunmer Spring MVC 配置
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
	// 当前激活的配置文件
	@Value("${spring.profiles.active}")
	private String env;

	// 使用阿里 FastJson 作为JSON MessageConverter
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(
				// 保留空的字段
				SerializerFeature.WriteMapNullValue,
				// String null -> ""
				SerializerFeature.WriteNullStringAsEmpty,
				// Number null -> 0
				SerializerFeature.WriteNullNumberAsZero);
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		converters.add(converter);
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	// 统一异常处理
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new HandlerExceptionResolver() {
			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
					Object handler, Exception e) {
				ResultCode result = null;
				if (handler instanceof HandlerMethod) {
					HandlerMethod handlerMethod = (HandlerMethod) handler;

					if (e instanceof ServiceException) {
						// 业务失败的异常，如“账号或密码错误”
						result = ResultCode.getFailure(ResultCode.CODE_UNKNOWN_ERROR, e.getMessage());
						logger.info(e.getMessage());
					} else {
						result = ResultCode.getFailure(ResultCode.INTERNAL_SERVER_ERROR,
								"接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
						String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s", request.getRequestURI(),
								handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod().getName(),
								e.getMessage());
						logger.error(message, e);
					}
				} else {
					if (e instanceof NoHandlerFoundException) {
						result = ResultCode.getFailure(ResultCode.NOT_FOUND,
								"接口 [" + request.getRequestURI() + "] 不存在");
					} else {
						result = ResultCode.getFailure(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
						logger.error(e.getMessage(), e);
					}
				}
				responseResult(response, result);
				return new ModelAndView();
			}

		});
	}

	// 解决跨域问题
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").maxAge(3000).allowCredentials(false);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}

	// 添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 接口签名认证拦截器，该签名认证比较简单，实际项目中建议使用Json Web Token代替。
		// 开发环境忽略签名认证
		if (!StringUtils.contains(env, ProfilesConstant.PROFILES_DEV)) {
			registry.addInterceptor(new HandlerInterceptorAdapter() {
				@Override
				public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
						throws Exception {
					String sign = request.getParameter("sign");
					// 验证签名
					if (StringUtils.isNotEmpty(sign) && validateSign(request, sign)) {
						return true;
					} else {
						logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}" + request.getRequestURI(), WebUtils.getIpAddress(request),
								JSON.toJSONString(request.getParameterMap()));
						ResultCode result = ResultCode.getFailure(ResultCode.UNAUTHORIZED, "签名认证失败");
						responseResult(response, result);
						return false;
					}
				}
			});
		}
		registry.addInterceptor(new GateWayAuthenticationInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/configuration/**").excludePathPatterns("/v2/api-docs/**")
				.excludePathPatterns("/webjars/**").excludePathPatterns("/swagger-ui/**")
				.excludePathPatterns("/swagger-resources/**")
				//测试模拟请求，生成sign，暂时不拦截，不需要验签
				.excludePathPatterns("/gateway/sign/**");
		super.addInterceptors(registry);
	}

	private void responseResult(HttpServletResponse response, ResultCode result) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(200);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 一个简单的签名认证，规则：请求参数按ASCII码排序后，拼接为a=value&b=value...这样的字符串后进行MD5
	 *
	 * @param request
	 * @param requestSign
	 * @return
	 */
	private boolean validateSign(HttpServletRequest request, String requestSign) {
		List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
		Collections.sort(keys);

		String linkString = "";

		for (String key : keys) {
			if (!"sign".equals(key)) {
				linkString += key + "=" + request.getParameter(key) + "&";
			}
		}
		if (StringUtils.isEmpty(linkString)) {
			return false;
		}

		linkString = linkString.substring(0, linkString.length() - 1);
		String key = "Potato";
		String sign = DigestUtils.md5Hex(linkString + key);

		return StringUtils.equals(sign, requestSign);

	}


}
