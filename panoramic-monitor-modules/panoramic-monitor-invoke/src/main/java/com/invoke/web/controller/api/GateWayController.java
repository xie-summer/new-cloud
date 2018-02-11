package com.invoke.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.cloud.util.*;
import com.google.common.collect.Maps;
import com.invoke.api.api.ApiConfigure;
import com.invoke.util.SignUtil;
import com.invoke.web.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author summer api对外接口网关
 */
@Api
@RestController
public class GateWayController {
    protected static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(GateWayController.class);

    @RequestMapping("/gateway")
    public HttpResult getApiAuth(@RequestParam Map<String, String> params, HttpServletRequest req,
                                 HttpServletResponse resp) {
        if (null == params || params.size() == 0) {
            params = WebUtils.getRequestMap(req);
        }
        StringBuffer sb = new StringBuffer();
        String method = params.get("method") + "";
        String biz_content = params.get("biz_content");
        Map bizMap = JsonUtils.readJsonToMap(biz_content);
        if (null != bizMap && bizMap.size() > 0) {
            bizMap.forEach((k, v) -> {
                sb.append(v + "/");
            });
        }
        try {
            req.getRequestDispatcher(method + "/" + sb.deleteCharAt(sb.length() - 1).toString()).forward(req, resp);
        } catch (Exception e) {
            DB_LOGGER.error("转发失败");
            HttpResult.getFailure("接口调度失败", 500, JsonUtils.writeMapToJson(params));
        }
        return null;
    }

    @RequestMapping("/gateway/apiAuthFailure")
    public HttpResult getApiAuthFailure(@RequestParam Map<String, String> params, HttpServletRequest req, HttpServletResponse resp) {
        DB_LOGGER.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}" + req.getRequestURI() + WebUtils.getIpAddress(req) +
                JSON.toJSONString(req.getParameterMap()));
        return HttpResult.getFailure("签名验证失败", 401, JsonUtils.writeMapToJson(params));
    }

    @ApiOperation(value = "GET 请求网关test", notes = "GET 请求网关授权验证test")
    @GetMapping("/gateway/sign")
    public HttpResult testGetApiAuth(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> params = Maps.newHashMap();
        params.put("app_id", ApiConfigure.APPID);
        params.put("method", ApiConfigure.QUERYURL);
        params.put("charset", ApiConfigure.CHARSET);
        params.put("sign_type", "RSA");
        params.put("timestamp", DateUtil.getCurFullTimestampStr());
        params.put("version", "1.0");
        Map<String, String> bizMap = Maps.newHashMap();
        bizMap.put("id", "1");
        params.put("biz_content", JsonUtils.writeMapToJson(bizMap));
        String sign = SignUtil.getSign(params, ApiConfigure.PRIVATEKEY);
        params.put("sign", sign);// TODO 签名必须URL编码两次

        // try {
        // req.getRequestDispatcher(BaseWebUtils.getRequestNamePortPath(req) +
        // ApiConfigure.QUERYURL).forward(req,
        // resp);
        // } catch (ServletException e) {
        // e.printStackTrace();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // // 发post请求
        // HttpResult postHttpResult = HttpUtils
        // .postUrlAsString(BaseWebUtils.getRequestNamePortPath(req) +
        // ApiConfigure.QUERYURL, params);
        // HttpResult getHttpResult =
        // HttpUtils.getUrlAsString(BaseWebUtils.getRequestNamePortPath(req)
        // + ApiConfigure.QUERYURL + "?token=" + JsonUtils.writeMapToJson(params));
        return null;
    }
}
