package com.cloud.zuul.componet.filter.pre;

import com.cloud.zuul.common.exception.RateLimiterException;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

/** @author summer 基于服务级别限流 */
@Component
public class ServiceRateLimitZuulFilter extends ZuulFilter {
  private Map<String, RateLimiter> map = Maps.newConcurrentMap();

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    // 这边的order一定要大于org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter的order
    // 也就是要大于5
    // 否则，RequestContext.getCurrentContext()里拿不到serviceId等数据。
    return Ordered.LOWEST_PRECEDENCE;
  }

  @Override
  public boolean shouldFilter() {
    // 这里可以考虑弄个限流开启的开关，开启限流返回true，关闭限流返回false，你懂的。
    return true;
  }

  @Override
  public Object run() {
    try {
      RequestContext context = RequestContext.getCurrentContext();
      HttpServletResponse response = context.getResponse();
      String key = null;
      // 对于service格式的路由，走RibbonRoutingFilter
      String serviceId = (String) context.get(SERVICE_ID_KEY);
      if (serviceId != null) {
        key = serviceId;
        map.putIfAbsent(serviceId, RateLimiter.create(1000.0));
      }
      // 如果压根不走RibbonRoutingFilter，则认为是URL格式的路由
      else {
        // 对于URL格式的路由，走SimpleHostRoutingFilter
        URL routeHost = context.getRouteHost();
        if (routeHost != null) {
          String url = routeHost.toString();
          key = url;
          map.putIfAbsent(url, RateLimiter.create(2000.0));
        }
      }
      RateLimiter rateLimiter = map.get(key);
      if (!rateLimiter.tryAcquire()) {
        HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setStatus(httpStatus.value());
        response.getWriter().append(httpStatus.getReasonPhrase());
        context.setSendZuulResponse(false);
        throw new RateLimiterException(
            httpStatus.getReasonPhrase(), httpStatus.value(), httpStatus.getReasonPhrase());
      }
    } catch (Exception e) {
      ReflectionUtils.rethrowRuntimeException(e);
    }
    return null;
  }
}
