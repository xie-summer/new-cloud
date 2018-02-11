package com.monitor.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cloud.commons.api.ApiSysParamConstants;
import com.cloud.commons.sign.Sign;
import com.cloud.constant.api.ApiConstant;
import com.monitor.api.acl.ApiMobileService;
import com.monitor.model.api.ApiUser;
import com.monitor.web.controller.api.ApiAuth;

/**
 * API2.0身份过滤器
 *
 * @author sunmer
 */
public abstract class BaseApiAuthenticationFilter extends GenericFilterBean {
    private static ThreadLocal<ApiAuth> apiAuthLocal = new ThreadLocal<ApiAuth>();
    protected ApiFilterHelper apiFilterHelper;
    @Autowired
    @Qualifier("apiMobileService")
    private ApiMobileService apiMobileService;
    public static ApiAuth getApiAuth() {
        return apiAuthLocal.get();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        Long cur = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (ServletFileUpload.isMultipartContent(request)) {//包装文件上传请求
            request = new CommonsMultipartResolver().resolveMultipart(request);
        }
        String appkey = request.getParameter(ApiSysParamConstants.APPKEY);
        //用户身份校验
        ApiUser apiUser = apiMobileService.getApiUserByAppkey(appkey);
        if (apiUser == null) {
            ApiFilterHelper.writeErrorResponse(response, ApiConstant.CODE_PARTNER_NOT_EXISTS, "用户不存在");
            apiFilterHelper.apiLog(request, cur, false);//记录失败日志
            return;
        }
        String sign = request.getParameter(ApiSysParamConstants.SIGN);
        String privateKey = getPrivateKey(apiUser, request);
        //签名校验
        String signData = Sign.signMD5(ApiFilterHelper.getTreeMap(request), privateKey);
        if (!StringUtils.equalsIgnoreCase(sign, signData)) {
            ApiFilterHelper.writeErrorResponse(response, ApiConstant.CODE_PARTNER_NORIGHTS, "校验签名错误!");
            apiFilterHelper.apiLog(request, cur, false);//记录失败日志
            return;
        }

        //权限校验
        boolean hasRights = checkRights(apiUser, request);
        if (!hasRights) {
            ApiFilterHelper.writeErrorResponse(response, ApiConstant.CODE_PARTNER_NORIGHTS, "没有权限");
            apiFilterHelper.apiLog(request, cur, false);//记录失败日志
            return;
        }

        //综上条件校验通过
        try {
            apiAuthLocal.set(new ApiAuth(apiUser));
            //执行下面方法链
            chain.doFilter(request, response);
        } finally {
            //清除当前授权用户
            apiAuthLocal.set(null);
            apiAuthLocal.remove();
            //记录成功日志
            apiFilterHelper.apiLog(request, cur, true);
        }

    }

    protected boolean checkRights(ApiUser user, HttpServletRequest request) {
        if (user == null) {
            return false;
        }
        if (!user.isEnabled()) {
            return false;
        }
        return true;
//        return rightsHelper.hasRights(user.getRoles(), request);
    }

    protected abstract String getPrivateKey(ApiUser apiUser, HttpServletRequest request);


    @Override
    protected void initFilterBean() throws ServletException {
//        List<WebModule> moduleList = apiSecureService.getApiModuleList();
//        rightsHelper = new RoleUrlMatchHelper(moduleList);
        apiFilterHelper = new ApiFilterHelper();
    }

}
