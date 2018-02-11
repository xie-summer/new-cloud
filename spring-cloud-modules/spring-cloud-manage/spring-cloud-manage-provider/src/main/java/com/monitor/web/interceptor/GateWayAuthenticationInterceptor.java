package com.monitor.web.interceptor;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author summer 拦截器
 */
public class GateWayAuthenticationInterceptor implements HandlerInterceptor {
    protected static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(GateWayAuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
