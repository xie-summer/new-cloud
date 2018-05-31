package com.cloud.zuul.componet.filter.pre;

import com.cloud.zuul.common.exception.RateLimiterException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/** @author summer 基于redis分布式限流 */
@Component
public class RedisRateLimitZuulFilter extends ZuulFilter {
  private static final String TIME_KEY = "";
  private static final String COUNTER_KEY = "";

  @Autowired private RedisTemplate redisTemplate;

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  @Override
  public boolean shouldFilter() {
    // 这里可以考虑弄个限流开启的开关，开启限流返回true，关闭限流返回false，你懂的。
    return true;
  }

  @Override
  public Object run() {
    try {
      RequestContext currentContext = RequestContext.getCurrentContext();
      HttpServletResponse response = currentContext.getResponse();
      if (!redisTemplate.hasKey(TIME_KEY)) {

        redisTemplate.opsForValue().set(TIME_KEY, 0, 1, TimeUnit.SECONDS);
        redisTemplate.opsForHash().put("lock", TIME_KEY, 0);
        redisTemplate.opsForHash().put("lock", COUNTER_KEY, 0);
        redisTemplate.expire("lock", 1, TimeUnit.SECONDS);
      }
      if (redisTemplate.hasKey(TIME_KEY)
          && redisTemplate.opsForValue().increment(COUNTER_KEY, 1) > 400) {
        HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setStatus(httpStatus.value());
        response.getWriter().append(httpStatus.getReasonPhrase());
        currentContext.setSendZuulResponse(false);
        throw new RateLimiterException(
            httpStatus.getReasonPhrase(), httpStatus.value(), httpStatus.getReasonPhrase());
      }

    } catch (Throwable e) {
      ReflectionUtils.rethrowRuntimeException(e);
    }
    return null;
  }
}
