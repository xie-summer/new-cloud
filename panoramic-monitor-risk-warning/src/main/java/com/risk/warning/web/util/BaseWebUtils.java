package com.risk.warning.web.util;

import com.cloud.constant.IpTypeConstants;
import com.cloud.constant.MarkConstant;
import com.cloud.util.*;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author summer
 */
public class BaseWebUtils {
    public static final String HTTP_REQUEST_METHOD_GET = "get";
    public static final String HTTP_REQUEST_METHOD_POST = "post";
    public static final String HTTP_REQUEST_TOKEN = "token";
    protected static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(BaseWebUtils.class);
    private static final List<String> DEFAULT_SENSITIVE = Arrays.asList("mobile", "pass", "sign", "encode", "token",
            "check", "card");
    private static final List<String> IGNORE_KEYS = Arrays.asList("mobileType");
    private static Pattern QUERY_MAP_PATTERN = Pattern.compile("&?([^=&]+)=");

    public BaseWebUtils() {
    }

    public static HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    public static final String getRemoteIp(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String result = getRemoteIpFromXfwd(xfwd);
        return StringUtils.isNotBlank(result) ? result : request.getRemoteAddr();
    }

    public static final String getRemoteIpFromXfwd(String xfwd) {
        String gewaip = null;
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");

            for (int i = ipList.length - 1; i >= 0; --i) {
                String ip = ipList[i];
                ip = StringUtils.trim(ip);
                if (IpConfig.isGewaServerIp(ip)) {
                    gewaip = ip;
                } else if (!"127.0.0.1".equals(ip) && !"localhost".equals(ip)) {
                    return ip;
                }
            }
        }

        return gewaip;
    }

    /**
     * @param req
     * @return http://localhost:8080/Example/
     */
    public static final String getRequestNamePortPath(HttpServletRequest req) {
        String contextPath = req.getContextPath();
        if (!StringUtils.endsWith(contextPath, "/")) {
            contextPath = contextPath + "/";
        }
        return "http://" + req.getServerName() + ":" + req.getServerPort() + contextPath;
    }

    /**
     * @param req
     * @return http://localhost:8080/Example/AServlet?
     */
    public static final String getRequestNamePortPathAndSerlevetPath(HttpServletRequest req) {
        return getRequestNamePortPath(req) + req.getServletPath();
    }

    public static final boolean isLocalRequest(HttpServletRequest request) {
        String ip = getRemoteIp(request);
        return IpConfig.isLocalIp(ip);
    }

    /**
     * 是否是 GET 请求
     *
     * @param request
     * @return
     */
    public static final boolean isGetRequest(HttpServletRequest request) {
        String method = request.getMethod();
        return HTTP_REQUEST_METHOD_GET.equalsIgnoreCase(method);
    }

    public static final boolean isPostRequest(HttpServletRequest request) {
        String method = request.getMethod();
        return HTTP_REQUEST_METHOD_POST.equalsIgnoreCase(method);
    }

    public static final void writeJsonResponse(HttpServletResponse res, boolean success, String retval) {
        res.setContentType("application/json;charset=utf-8");
        res.setCharacterEncoding("utf-8");

        try {
            PrintWriter writer = res.getWriter();
            Map jsonMap = Maps.newHashMap();
            jsonMap.put("success", success);
            if (!success) {
                jsonMap.put("msg", retval);
            } else {
                jsonMap.put("retval", retval);
            }

            writer.write("var data=" + JsonUtils.writeObjectToJson(jsonMap));
            res.flushBuffer();
        } catch (IOException var5) {
            ;
        }

    }

    public static final String getAttributeStr(HttpServletRequest request, String spliter) {
        String paramsStr = "";

        String tmpname;
        for (Enumeration params = request.getAttributeNames(); params
                .hasMoreElements(); paramsStr = paramsStr + tmpname + "=" + request.getAttribute(tmpname) + spliter) {
            tmpname = (String) params.nextElement();
        }

        return paramsStr;
    }

    public static final String getHeaderStr(HttpServletRequest request) {
        return "" + getHeaderMap(request);
    }

    public static final Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> result = Maps.newHashMap();
        Enumeration<String> it = request.getParameterNames();
        String key = null;

        while (it.hasMoreElements()) {
            key = (String) it.nextElement();
            result.put(key, request.getParameter(key));
        }

        return result;
    }

    public static final Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> result = Maps.newHashMap();
        Enumeration<String> it = request.getHeaderNames();

        String value;
        for (String key = null; it.hasMoreElements(); result.put(key, value)) {
            key = (String) it.nextElement();
            value = request.getHeader(key);
            if (StringUtils.containsIgnoreCase(key, "cookie")) {
                value = "*******";
            }
        }

        return result;
    }

    public static final Map<String, String> getHeaderMapWidthPreKey(HttpServletRequest request) {
        Map<String, String> result = Maps.newHashMap();
        Enumeration<String> it = request.getHeaderNames();

        String value;
        for (String key = null; it.hasMoreElements(); result.put("head4" + StringUtils.lowerCase(key), value)) {
            key = (String) it.nextElement();
            value = request.getHeader(key);
            if (StringUtils.containsIgnoreCase(key, "cookie")) {
                value = "*******";
            }
        }

        return result;
    }

    public static final List<String> getPictures(String html) {
        return HtmlParser.getNodeAttrList(html, "img", "src");
    }

    public static final List<String> getNodeAttrList(String html, String nodename, String attrName) {
        return HtmlParser.getNodeAttrList(html, nodename, attrName);
    }

    public static final List<String> getVideos(String html) {
        return HtmlParser.getNodeAttrList(html, "embed", "src");
    }

    public static final void clearCookie(HttpServletResponse response, String path, String cookieName) {
        Cookie cookie = new Cookie(cookieName, (String) null);
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public static final boolean isRobot(String userAgent) {
        return StringUtils.containsIgnoreCase(userAgent, "spider")
                || StringUtils.containsIgnoreCase(userAgent, "Googlebot")
                || StringUtils.containsIgnoreCase(userAgent, "robot");
    }

    public static final boolean isAjaxRequest(HttpServletRequest request) {
        boolean result = StringUtils.isNotBlank(request.getHeader("X-Requested-With"));
        return result;
    }

    public static final void addCookie(HttpServletResponse response, String cookiename, String cookievalue, String path,
                                       int maxSecond) {
        Cookie cookie = new Cookie(cookiename, cookievalue);
        cookie.setPath(path);
        cookie.setMaxAge(maxSecond);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static final Cookie getCookie(HttpServletRequest request, String cookiename) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        } else {
            Cookie[] var6 = cookies;
            int var5 = cookies.length;

            for (int var4 = 0; var4 < var5; ++var4) {
                Cookie cookie = var6[var4];
                if (cookiename.equals(cookie.getName())) {
                    return cookie;
                }
            }

            return null;
        }
    }

    public static final String getCookieValue(HttpServletRequest request, String cookiename) {
        Cookie cookie = getCookie(request, cookiename);
        return cookie == null ? null : cookie.getValue();
    }

    public static final String joinParams(Map params, boolean ignoreBlank) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        Iterator var5 = keys.iterator();

        while (true) {
            String key;
            Object value;
            do {
                if (!var5.hasNext()) {
                    if (content.length() > 0) {
                        content.deleteCharAt(content.length() - 1);
                    }

                    return content.toString();
                }

                key = (String) var5.next();
                value = params.get(key);
            } while (ignoreBlank && (value == null || !StringUtils.isNotBlank("" + value)));

            content.append(key + "=" + value + "&");
        }
    }

    public static final boolean checkString(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (StringUtils.contains(StringUtils.lowerCase(str), "<script")) {
            return true;
        } else {
            return StringUtils.contains(StringUtils.lowerCase(str), "<iframe");
        }
    }

    public static final boolean checkPropertyAll(Object entity) {
        try {
            Map result = PropertyUtils.describe(entity);
            Iterator var3 = result.keySet().iterator();

            while (var3.hasNext()) {
                Object key = var3.next();
                if (result.get(key) instanceof String && checkString("" + result.get(key))) {
                    return true;
                }
            }
        } catch (Exception var4) {
            ;
        }

        return false;
    }

    public static final Map<String, String> getRequestParams(HttpServletRequest request, String... pnames) {
        Map<String, String> result = new TreeMap();
        if (pnames != null) {
            String[] var6 = pnames;
            int var5 = pnames.length;

            for (int var4 = 0; var4 < var5; ++var4) {
                String pn = var6[var4];
                String pv = request.getParameter(pn);
                if (StringUtils.isNotBlank(pv)) {
                    result.put(pn, pv);
                }
            }
        }

        return result;
    }

    public static final String getBrowerInfo(String userAgent) {
        String browserInfo = "UNKNOWN";
        String info = StringUtils.lowerCase(userAgent);

        try {
            String[] strInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")") - 1).split(";");
            if (info.indexOf("msie") > -1) {
                return strInfo[1].trim();
            }

            String[] str = info.split(" ");
            if (info.indexOf("navigator") < 0 && info.indexOf("firefox") > -1) {
                return str[str.length - 1].trim();
            }

            if (info.indexOf("opera") > -1) {
                return str[0].trim();
            }

            if (info.indexOf("chrome") < 0 && info.indexOf("safari") > -1) {
                return str[str.length - 1].trim();
            }

            if (info.indexOf("chrome") > -1) {
                return str[str.length - 2].trim();
            }

            if (info.indexOf("navigator") > -1) {
                return str[str.length - 1].trim();
            }
        } catch (Exception var5) {
            ;
        }

        return browserInfo;
    }

    public static final Map<String, String> parseQueryStr(String queryString, String encode) {
        Map<String, String> map = new LinkedHashMap();
        if (StringUtils.isBlank(queryString)) {
            return map;
        } else {
            Matcher matcher = QUERY_MAP_PATTERN.matcher(queryString);
            String key = null;

            String value;
            int end;
            for (end = 0; matcher.find(); end = matcher.end()) {
                if (key != null) {
                    try {
                        value = queryString.substring(end, matcher.start());
                        if (StringUtils.isNotBlank(value)) {
                            value = URLDecoder.decode(value, encode);
                            map.put(key, value);
                        }
                    } catch (UnsupportedEncodingException var9) {
                        DB_LOGGER.error("", var9);
                    }
                }

                key = matcher.group(1);
            }

            if (key != null) {
                try {
                    value = queryString.substring(end);
                    if (StringUtils.isNotBlank(value)) {
                        value = URLDecoder.decode(value, encode);
                        map.put(key, value);
                    }
                } catch (UnsupportedEncodingException var8) {
                    DB_LOGGER.error("", var8);
                }
            }

            return map;
        }
    }

    public static final String getQueryStr(HttpServletRequest request, String encode) {
        return getQueryStr(flatRequestMap(request.getParameterMap(), ","), encode);
    }

    public static final Map<String, String> flatRequestMap(Map<String, String[]> reqMap, String joinChar) {
        Map<String, String> flatMap = Maps.newHashMap();
        Iterator var4 = reqMap.keySet().iterator();

        while (var4.hasNext()) {
            String key = (String) var4.next();
            flatMap.put(key, StringUtils.join((Object[]) reqMap.get(key), joinChar));
        }

        return flatMap;
    }

    public static final String getQueryStr(Map<String, String> requestMap, String encode) {
        if (requestMap != null && !requestMap.isEmpty()) {
            Iterator var4 = requestMap.keySet().iterator();
            StringBuilder sb = new StringBuilder();
            while (var4.hasNext()) {
                String name = (String) var4.next();

                try {
                    sb.append(name).append("=").append(URLEncoder.encode((String) requestMap.get(name), encode)).append("&");
                } catch (UnsupportedEncodingException var6) {
                }
            }

            return sb.substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }

    public static final String encodeParam(String params, String encode) {
        Map<String, String> paramMap = parseQueryStr(params, encode);
        String result = "";
        Iterator var5 = paramMap.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (var5.hasNext()) {
            String key = (String) var5.next();

            try {
                sb.append("&").append(key).append("=").append(URLEncoder.encode((String) paramMap.get(key), encode));
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
            }
        }

        return StringUtils.isNotBlank(sb.toString()) ? sb.substring(1) : "";
    }

    public static final String getContextPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if (!StringUtils.endsWith(contextPath, "/")) {
            contextPath = contextPath + "/";
        }

        return contextPath;
    }

    public static final String getParamStr(HttpServletRequest request, boolean removeSensitive,
                                           String... sensitiveKeys) {
        Map<String, String> requestMap = getRequestMap(request);
        if (removeSensitive) {
            removeSensitiveInfo(requestMap, sensitiveKeys);
        }

        return "" + requestMap;
    }

    public static final void removeSensitiveInfo(Map<String, String> params, String... keys) {
        List<String> keyList = null;
        if (keys != null) {
            keyList = new ArrayList(DEFAULT_SENSITIVE);
            ((List) keyList).addAll(Arrays.asList(keys));
        } else {
            keyList = DEFAULT_SENSITIVE;
        }

        Iterator var4 = (new ArrayList(params.keySet())).iterator();

        while (true) {
            String pname;
            do {
                if (!var4.hasNext()) {
                    return;
                }

                pname = (String) var4.next();
                int valueLen = StringUtils.length((String) params.get(pname));
                if (valueLen > 1000) {
                    params.put(pname, StringUtils.substring((String) params.get(pname), 1000) + "->LEN:" + valueLen);
                }
            } while (IGNORE_KEYS.contains(pname));

            Iterator var7 = ((List) keyList).iterator();

            while (var7.hasNext()) {
                String key = (String) var7.next();
                if (StringUtils.containsIgnoreCase(pname, key) && StringUtils.isNotBlank((String) params.get(pname))) {
                    params.put(pname, "MG" + StringUtil.md5("kcj3STidSC" + (String) params.get(pname)));
                }
            }
        }
    }

    public static final String getRemotePort(HttpServletRequest request) {
        String port = request.getHeader("x-client-port");
        return StringUtils.isBlank(port) ? "" + request.getRemotePort() : port;
    }

    public static final String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || IpTypeConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(MarkConstant.COMMA) != -1) {
            ip = ip.substring(0, ip.indexOf(MarkConstant.COMMA)).trim();
        }

        return ip;
    }

    public String checkScript(HttpServletRequest request) {
        String match = "onclick|onfocus|onblur|onload|onerror";
        Iterator var4 = request.getParameterMap().values().iterator();

        while (var4.hasNext()) {
            String[] v = (String[]) var4.next();
            String[] var8 = v;
            int var7 = v.length;

            for (int var6 = 0; var6 < var7; ++var6) {
                String value = var8[var6];
                String script = StringUtil.findFirstByRegex(value, match);
                if (StringUtils.isNotBlank(script)) {
                    return script;
                }
            }
        }

        return "";
    }
}
