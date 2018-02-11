package com.invoke.web.util;

import com.cloud.support.ErrorCode;
import com.cloud.support.TraceErrorException;
import com.cloud.util.DateUtil;
import com.cloud.util.IpConfig;
import com.cloud.util.SystemUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.UnmodifiableMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.*;

/**
 * @author summer
 */
public class Config implements InitializingBean {
    public static final String SYSTEMID;
    public static final String DEPLOYID;
    public static final String SESSION_COOKIE_NAME;
    private static boolean isHoutai = false;
    private static boolean isPreEnv = false;
    private static boolean isTestEnv = false;
    private static final Properties PROPS = new Properties();
    private Map<String, String> configMap = new HashMap();
    private Map<String, Object> pageMap = new HashMap();
    private static Map pageTools;
    private boolean initedConfig = false;
    private boolean initedPage = false;

    static {
        try {
            PROPS.load(Config.class.getClassLoader().getResourceAsStream("gewa-global.properties"));
        } catch (IOException var1) {
            throw new TraceErrorException("", var1);
        }

        SYSTEMID = PROPS.getProperty("systemid");
        DEPLOYID = SYSTEMID + "-" + SystemUtils.getShortHostname(IpConfig.getHostname());
        if (StringUtils.isNotBlank(PROPS.getProperty("sessionCookieName"))) {
            SESSION_COOKIE_NAME = PROPS.getProperty("sessionCookieName");
        } else {
            SESSION_COOKIE_NAME = SYSTEMID.toLowerCase() + "_uskey_";
        }

        List idList;
        if (StringUtils.isNotBlank(System.getenv("HOUTAI_SYSTEMID"))) {
            idList = Arrays.asList(StringUtils.split(System.getenv("HOUTAI_SYSTEMID"), ","));
            isHoutai = idList.contains(SYSTEMID) || idList.contains("ALL");
        }

        if (StringUtils.isNotBlank(System.getenv("PRE_ENV_SYSTEMID"))) {
            idList = Arrays.asList(StringUtils.split(System.getenv("PRE_ENV_SYSTEMID"), ","));
            isPreEnv = idList.contains(SYSTEMID) || idList.contains("ALL");
        }

        if (StringUtils.isNotBlank(System.getenv("TEST_ENV_SYSTEMID"))) {
            idList = Arrays.asList(StringUtils.split(System.getenv("TEST_ENV_SYSTEMID"), ","));
            isTestEnv = idList.contains(SYSTEMID) || idList.contains("ALL");
        }

    }

    public Config() {
    }

    public static boolean isTestEnv() {
        return isTestEnv;
    }

    public static boolean isPreEnv() {
        return isPreEnv;
    }

    public static boolean isHoutai() {
        return isHoutai;
    }

    public static Map getPageTools() {
        return pageTools;
    }

    public String getGlobalProp(String key) {
        return PROPS.getProperty(key);
    }

    public String getString(String key) {
        String result = (String)this.configMap.get(key);
        if (StringUtils.isBlank(result)) {
            result = pageTools.get(key) == null ? null : "" + pageTools.get(key);
        }

        return result;
    }

    public Long getLong(String key) {
        String result = this.getString(key);
        return StringUtils.isBlank(result) ? null : Long.parseLong(result);
    }

    public void setConfigMap(Map<String, String> configMap) {
        if (!this.initedConfig) {
            this.configMap = configMap;
            this.initedConfig = true;
        } else {
            throw new IllegalStateException("不能再次调用");
        }
    }

    public void setPageMap(Map<String, Object> pageMap) {
        if (!this.initedPage) {
            this.pageMap = pageMap;
            this.initedPage = true;
        } else {
            throw new IllegalStateException("不能再次调用");
        }
    }

    public Map<String, Object> getPageMap() {
        return new HashMap(this.pageMap);
    }

    public static String getServerIp() {
        return IpConfig.getServerip();
    }

    public static String getHostname() {
        return IpConfig.getHostname();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.configMap = UnmodifiableMap.decorate(this.configMap);
        this.initPageTools();
    }

    public void initPageTools() {
        Map tmp = Maps.newHashMap();
//        tmp.put("math", new MathTool());
        tmp.put("DateUtil", new DateUtil());
        tmp.putAll(this.pageMap);
        pageTools = UnmodifiableMap.decorate(tmp);
    }

    public ErrorCode replacePageTool(String property, Object value) {
        Object old = pageTools.get(property);
        if (value != null && old != null) {
            if (!value.getClass().equals(old.getClass())) {
                return ErrorCode.getFailure("参数类型不兼容");
            } else {
                Map tmp = new HashMap(pageTools);
                tmp.put(property, value);
                pageTools = UnmodifiableMap.decorate(tmp);
                if (this.pageMap.containsKey(property)) {
                    this.pageMap.put(property, value);
                }

                return ErrorCode.SUCCESS;
            }
        } else {
            return ErrorCode.getFailure("参数错误:old 或 new 为空");
        }
    }

    public String getBasePath() {
        return (String)this.pageMap.get("basePath");
    }

    public String getAbsPath() {
        return (String)this.pageMap.get("absPath");
    }

    public String getCacheVersionKey() {
        return this.getString("cacheVersionKey");
    }

    public static boolean isGewaServerIp(String ip) {
        return IpConfig.isGewaServerIp(ip);
    }
}
