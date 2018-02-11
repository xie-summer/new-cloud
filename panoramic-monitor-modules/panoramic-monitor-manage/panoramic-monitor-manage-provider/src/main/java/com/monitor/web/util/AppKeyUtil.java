package com.monitor.web.util;

import com.google.common.collect.Maps;
import org.apache.commons.collections.map.UnmodifiableMap;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author summer
 */
public class AppKeyUtil {

    private static boolean INITIALIZED = false;

    private static Map<String, String> APP_KEY_MAP;

    public static synchronized void init(String propertyFile) {
        if (INITIALIZED) {
            return;
        }
        Properties props = new Properties();
        InputStream is = null;
        try {
            is = AppKeyUtil.class.getClassLoader().getResourceAsStream(propertyFile);
            props.load(is);
        } catch (Exception e) {
            throw new IllegalArgumentException("property File Error!!!!", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        Map<String, String> tmp = Maps.newHashMap();
        Enumeration<?> propertyNames = props.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();
            String propertyValue = props.getProperty(propertyName);
            tmp.put(propertyName, propertyValue);
        }
        APP_KEY_MAP = UnmodifiableMap.decorate(tmp);

        INITIALIZED = true;
    }

    public static String getPrivateKey(String tag) {
        return APP_KEY_MAP.get(tag);
    }
}
