package com.invoke.web.filter;

import com.cloud.commons.api.ApiSysParamConstants;
import com.cloud.commons.sign.Sign;
import com.cloud.constant.api.ApiConstant;
import com.invoke.model.api.ApiUser;
import com.invoke.web.controller.api.ApiAuth;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author summer
 */
public class InnerApiAuthFilter extends GenericFilterBean {
    private static ThreadLocal<ApiAuth> apiAuthLocal = new ThreadLocal<ApiAuth>();
    private String[] innerIpList;
    private ApiFilterHelper apiFilterHelper;

    public static ApiAuth getApiAuth() {
        return apiAuthLocal.get();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        long cur = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
//包装文件上传请求
        if (ServletFileUpload.isMultipartContent(request)) {
            request = new CommonsMultipartResolver().resolveMultipart(request);
        }

        String sign = request.getParameter(ApiSysParamConstants.SIGN);
        String appkey = request.getParameter(ApiSysParamConstants.APPKEY);

        //用户身份校验
//        ApiUser apiUser = daoService.getObjectByUkey(ApiUser.class, "partnerkey", appkey, true);
        ApiUser apiUser = new ApiUser(); //TODO
        if (apiUser == null) {
            ApiFilterHelper.writeErrorResponse(response, ApiConstant.CODE_PARTNER_NOT_EXISTS, "用户不存在");
            apiFilterHelper.apiLog(request, cur, false);
            return;
        }

        //签名校验
        String signData = Sign.signMD5(ApiFilterHelper.getTreeMap(request), apiUser.getPrivatekey());
        if (!StringUtils.equalsIgnoreCase(sign, signData)) {
            ApiFilterHelper.writeErrorResponse(response, ApiConstant.CODE_PARTNER_NORIGHTS, "校验签名错误!");
            apiFilterHelper.apiLog(request, cur, false);
            return;
        }
//WebUtils.getRemoteIp(request);
        String remoteIp = "";
        if (!isInnerIp(remoteIp)) {
            ApiFilterHelper.writeErrorResponse(response, ApiConstant.CODE_PARTNER_NORIGHTS, "没有权限");
            return;
        }
        //综上条件校验通过
        try {
            //保存当前授权用户
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

    @Override
    protected void initFilterBean() throws ServletException {
        innerIpList = new String[]{"172.22.1.", "192.168.", "180.153.146.1", "114.80.171.2", "127.0.0.1"};
        apiFilterHelper = new ApiFilterHelper();
    }


    private boolean isInnerIp(String remoteIp) {
        for (String ipPre : innerIpList) {
            if (StringUtils.startsWith(remoteIp, ipPre)) {
                return true;
            }
        }
        return false;
    }
}
