package com.invoke.web.filter;

import com.cloud.commons.api.ApiSysParamConstants;
import com.cloud.constant.api.ApiConstant;
import com.invoke.api.acl.ApiMobileService;
import com.invoke.model.api.ApiUser;
import com.invoke.web.controller.api.ApiAuth;
import com.invoke.web.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * API2.0身份过滤器
 *
 * @author summer
 */
public class OpenApiPartnerAuthenticationFilter extends GenericFilterBean {
    private static ThreadLocal<ApiAuth> apiAuthLocal = new ThreadLocal<ApiAuth>();
    @Autowired
    @Qualifier("apiMobileService")
    private ApiMobileService apiMobileService;
    private String[] innerIpList;

    public static ApiAuth getApiAuth() {
        return apiAuthLocal.get();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String remoteIp = WebUtils.getRemoteIp(request);
        if (!isInnerIp(remoteIp)) {
            response.sendError(403, "只能内部调用！");
            return;
        }
        String appkey = request.getParameter(ApiSysParamConstants.APPKEY);
        ApiUser apiUser = apiMobileService.getApiUserByAppkey(appkey);
        if (apiUser == null) {
            ApiFilterHelper.writeErrorResponse(response, ApiConstant.CODE_PARTNER_NOT_EXISTS, "用户不存在");
            return;
        }
        String partnerIp = request.getHeader("DP-REMOTE-IP");

        try {
            apiAuthLocal.set(new ApiAuth(apiUser, partnerIp));
            chain.doFilter(request, response);
        } finally {
            apiAuthLocal.set(null);
            apiAuthLocal.remove();
        }
    }

    private boolean isInnerIp(String remoteIp) {
        for (String ipPre : innerIpList) {
            if (StringUtils.startsWith(remoteIp, ipPre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initFilterBean() throws ServletException {
        innerIpList = new String[]{"172.22.1.", "192.168.", "180.153.146.1", "114.80.171.2", "127.0.0.1"};
    }

}
