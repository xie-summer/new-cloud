package com.invoke.web.filter;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author summer 签名验签过滤器
 */
public class GateWayAuthenticationFilter extends GenericFilterBean {
    protected static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(GateWayAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Map<String, String> params = null;
        chain.doFilter(request, response);
        return;
    }

}
