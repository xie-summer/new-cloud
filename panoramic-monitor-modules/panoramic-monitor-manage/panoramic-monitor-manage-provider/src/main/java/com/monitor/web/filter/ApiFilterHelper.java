package com.monitor.web.filter;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.monitor.web.util.BaseWebUtils;
import com.monitor.web.util.WebUtils;

/**
 * @author summer
 */
public class ApiFilterHelper {
    public ApiFilterHelper() {
    }

    public static void writeErrorResponse(HttpServletResponse res, String code, String message) {
        res.setContentType("text/xml; charset=UTF-8");
        try {
            PrintWriter writer = res.getWriter();
            writer.write("<?xml  version=\"1.0\" encoding=\"UTF-8\" ?><data>");
            writer.write("<code>" + code + "</code>");
            writer.write("<error><![CDATA[" + message + "]]></error></data>");
        } catch (IOException e) {
        }
    }

    public static TreeMap<String, String> getTreeMap(HttpServletRequest request) {
        Map<String, String[]> requestParams = request.getParameterMap();
        TreeMap<String, String> params = new TreeMap<String, String>();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String[] values = entry.getValue();
            StringBuilder vb = new StringBuilder();
            for (String v : values) {
                vb.append(v + ",");
            }
            params.put(entry.getKey(), StringUtils.removeEnd(vb.toString(), ","));
        }
        if (ServletFileUpload.isMultipartContent(request)) {//文件FileData不做签名
            params.remove("FileData");
        }
        return params;
    }

    /**
     * 记录访问日志
     *
     * @param request 请求
     * @param
     */
    public void apiLog(HttpServletRequest request, Long calltime, boolean success) {
        Map<String, String> params = WebUtils.getRequestMap(request);
        BaseWebUtils.removeSensitiveInfo(params);
        if (params.containsKey("encryptCode")) {
            params.put("encryptCode", "****");
        } else {
            params.put("encryptCode", "none");
        }
        params.put("remoteip", WebUtils.getRemoteIp(request));
        params.put("uri", request.getRequestURI());
        params.put("callSuccess", "" + success);
//		monitorService.addApiLog(params, calltime);
    }

}
