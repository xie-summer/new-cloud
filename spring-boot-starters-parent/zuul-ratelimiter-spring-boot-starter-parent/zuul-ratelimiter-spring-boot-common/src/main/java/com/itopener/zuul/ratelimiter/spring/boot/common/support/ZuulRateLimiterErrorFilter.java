package com.itopener.zuul.ratelimiter.spring.boot.common.support;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.itopener.zuul.ratelimiter.spring.boot.common.ZuulRateLimiterProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @description 参照SendErrorFilter自定义处理超过限流配置的异常，支持转发到自定义配置的路径，不与error路径共用，方便单独对超过限流的情况做处理
 * @author fuwei.deng
 * @date 2018年2月2日 下午3:24:49
 * @version 1.0.0
 */
public class ZuulRateLimiterErrorFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(ZuulRateLimiterErrorFilter.class);
	
	/** error处理阶段的标记，需要与SendErrorFilter中的常量保持一致*/
	protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";

	private ZuulRateLimiterProperties zuulRateLimiterProperties;
	
	public ZuulRateLimiterErrorFilter(ZuulRateLimiterProperties zuulRateLimiterProperties) {
		super();
		this.zuulRateLimiterProperties = zuulRateLimiterProperties;
	}

	@Override
	public String filterType() {
		return FilterConstants.ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		// 在SendErrorFilter之前执行
		return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		// 在SendErrorFilter的基础上，增加了判断异常类型和是否配置了超过限流时的转发路径
		return ctx.getThrowable() != null
				&& !ctx.getBoolean(SEND_ERROR_FILTER_RAN, false)
				&& ctx.getThrowable().getCause() instanceof OverRateLimitException
				&& StringUtils.hasText(zuulRateLimiterProperties.getOverLimitPath());
	}

	@Override
	public Object run() {
		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			OverRateLimitException exception = (OverRateLimitException) ctx.getThrowable().getCause();
			logger.warn("OverRateLimitException,statusCode:{},errorCause:{}", exception.getCode(), exception.getMessage());
			HttpServletRequest request = ctx.getRequest();
			
			// 设置状态码和异常信息，方便自定义转发的请求中获取详细信息
			request.setAttribute("javax.servlet.error.status_code", exception.getCode());
			request.setAttribute("javax.servlet.error.exception", exception);

			if (StringUtils.hasText(exception.getMessage())) {
				request.setAttribute("javax.servlet.error.message", exception.getMessage());
			}
			request.setAttribute("exception", exception);
			
			// forward到自定义的异常路径，方便针对超过限流的情况单独处理，返回自定义配置的code和信息给调用者
			RequestDispatcher dispatcher = request.getRequestDispatcher(zuulRateLimiterProperties.getOverLimitPath());
			if (dispatcher != null) {
				// 设置SendErrorFilter中的标记，不用走SendErrorFilter的逻辑
				ctx.set(SEND_ERROR_FILTER_RAN, true);
				if (!ctx.getResponse().isCommitted()) {
					dispatcher.forward(request, ctx.getResponse());
				}
			}
		} catch (Exception ex) {
			ReflectionUtils.rethrowRuntimeException(ex);
		}
		return null;
	}

}
