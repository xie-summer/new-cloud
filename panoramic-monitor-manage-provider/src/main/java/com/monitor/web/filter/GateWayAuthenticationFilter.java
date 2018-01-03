package com.monitor.web.filter;

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
       
//        if ( ApiConfigure.GATEWAY_CALL_BACK.equals(request.getRequestURL())) {
//            chain.doFilter(request, response);
//            return;
//        }
//        params = WebUtils.getRequestMap(request);
//        if (null == params) {
//            params = Maps.newHashMap();
//        }
//        String sign = params.get("sign") + "";
//        String mehtod = params.get("method") + "";
//        // 移除sign再进行验签，原始数据验签
//        params.remove("sign");
//        String content = WebUtils.joinParams(params, true);
//        Boolean checkRsaSign = SignUtil.checkRsaSign(content, sign, ApiConfigure.PUBLICKEY, ApiConfigure.CHARSET);
//        if (!checkRsaSign) {
//            DB_LOGGER.error("验证签名失败:" + JsonUtils.writeMapToJson(params));
//            req.getRequestDispatcher(ApiConfigure.GATEWAY_CALL_BACK).forward(request, response);
//            return;
//        }
        chain.doFilter(request, response);
        return;
    }

}
