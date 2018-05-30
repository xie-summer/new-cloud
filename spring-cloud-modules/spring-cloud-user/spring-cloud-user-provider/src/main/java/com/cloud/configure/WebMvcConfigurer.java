package com.cloud.configure; // package com.cloud.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.cloud.api.vo.ResultCode;
import com.cloud.constant.IpTypeConstants;
import com.cloud.constant.MarkConstant;
import com.cloud.core.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/** @author sunmer Spring MVC 配置 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

  private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

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

  @Override
  public void configureHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    exceptionResolvers.add(
        new HandlerExceptionResolver() {
          @Override
          public ModelAndView resolveException(
              HttpServletRequest request,
              HttpServletResponse response,
              Object handler,
              Exception e) {
            ResultCode result = null;
            if (handler instanceof HandlerMethod) {
              HandlerMethod handlerMethod = (HandlerMethod) handler;

              if (e instanceof ServiceException) {
                // 业务失败的异常，如“账号或密码错误”
                result = ResultCode.getFailure(ResultCode.CODE_UNKNOWN_ERROR, e.getMessage());
                logger.info(e.getMessage());
              } else {
                result =
                    ResultCode.getFailure(
                        ResultCode.INTERNAL_SERVER_ERROR,
                        "接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                String message =
                    String.format(
                        "接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                        request.getRequestURI(),
                        handlerMethod.getBean().getClass().getName(),
                        handlerMethod.getMethod().getName(),
                        e.getMessage());
                logger.error(message, e);
              }
            } else {
              if (e instanceof NoHandlerFoundException) {
                result =
                    ResultCode.getFailure(
                        ResultCode.NOT_FOUND, "接口 [" + request.getRequestURI() + "] 不存在");
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

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
    registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
    super.addResourceHandlers(registry);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
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

  private String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    // 如果是多级代理，那么取第一个ip为客户ip
    if (ip != null && ip.indexOf(MarkConstant.COMMA) != -1) {
      ip = ip.substring(0, ip.indexOf(MarkConstant.COMMA)).trim();
    }

    return ip;
  }
}
