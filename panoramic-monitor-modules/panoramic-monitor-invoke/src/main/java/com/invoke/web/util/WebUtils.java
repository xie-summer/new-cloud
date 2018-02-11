package com.invoke.web.util;

import com.cloud.util.StringUtil;
import com.invoke.constant.AbstractCityConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author summer
 */
public class WebUtils extends BaseWebUtils {

    public static String removeFourChar(String content) {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        byte[] conbyte = content.getBytes();
        for (int i = 0; i < conbyte.length; i++) {
            if ((conbyte[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    conbyte[i + j] = 0x30;
                }
                i += 3;
            }
        }
        content = new String(conbyte);
        return content.replaceAll("0000", "");
    }

    public static String getShopingCartMemberKey(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, "memberKey");
        String memberKey = null;
        if (cookie != null) {
            memberKey = cookie.getValue();
        } else {
            String key = StringUtil.getRandomString(24);
            String ukey = key + StringUtil.md5(key + "ShopingCartMemberKey", 8);
            memberKey = ukey;
        }
        WebUtils.addCookie(response, "memberKey", memberKey, "/", 60 * 60 * 24);
        return memberKey;
    }

    public static String getAndSetCityCode(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getCookie(request, "citycode");
        String citycode = null;
        if (cookie != null) {
            citycode = cookie.getValue();
            if (isValidCitycode(citycode)) {
                return citycode;
            }
        }
        citycode = AbstractCityConstant.CITYCODE_SH;
        WebUtils.addCookie(response, "citycode", citycode, "/", 60 * 60 * 24 * 7);
        return citycode;
    }

    public static boolean isValidCitycode(String citycode) {
        return AbstractCityConstant.ALLCITY.contains(citycode);
    }

    public static String getCitycodeByIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        String citycode = findCitycodeByIp();
        if (StringUtils.isBlank(citycode)) {
            citycode = AbstractCityConstant.CITYCODE_SH;
        }
        return citycode;
    }

    /**    //没有默认值，特殊情况下使用
     * @return
     */

    public static String findCitycodeByIp() {
        return AbstractCityConstant.CITYCODE_SH;
    }

    public static void appendQueryProperties(StringBuilder targetUrl, LinkedHashMap<String, Object> model, String encoding) {
        boolean first = (targetUrl.indexOf("?") < 0);
        for (Map.Entry<String, Object> entry : queryProperties(model).entrySet()) {
            Object rawValue = entry.getValue();
            Iterator valueIter = null;
            if (rawValue != null && rawValue.getClass().isArray()) {
                valueIter = Arrays.asList(ObjectUtils.toObjectArray(rawValue)).iterator();
            } else if (rawValue instanceof Collection) {
                valueIter = ((Collection) rawValue).iterator();
            } else {
                valueIter = Collections.singleton(rawValue).iterator();
            }
            while (valueIter.hasNext()) {
                Object value = valueIter.next();
                if (first) {
                    targetUrl.append('?');
                    first = false;
                } else {
                    targetUrl.append('&');
                }
                String encodedKey = urlEncode(entry.getKey(), encoding);
                String encodedValue = (value != null ? urlEncode(value.toString(), encoding) : "");
                targetUrl.append(encodedKey).append('=').append(encodedValue);
            }
        }
    }

    private static Map<String, Object> queryProperties(Map<String, Object> model) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            if (isEligibleProperty(entry.getValue())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }


    private static boolean isEligibleProperty(Object value) {
        if (value == null) {
            return false;
        }
        if (isEligibleValue(value)) {
            return true;
        }

        if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            if (length == 0) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                Object element = Array.get(value, i);
                if (!isEligibleValue(element)) {
                    return false;
                }
            }
            return true;
        }

        if (value instanceof Collection) {
            Collection coll = (Collection) value;
            if (coll.isEmpty()) {
                return false;
            }
            for (Object element : coll) {
                if (!isEligibleValue(element)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    private static String urlEncode(String input, String charsetName) {
        try {
            return (input != null ? URLEncoder.encode(input, charsetName) : null);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static boolean isEligibleValue(Object value) {
        return (value != null && BeanUtils.isSimpleValueType(value.getClass()));
    }
}
