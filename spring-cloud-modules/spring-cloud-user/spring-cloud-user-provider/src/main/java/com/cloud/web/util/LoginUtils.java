package com.cloud.web.util;

import com.cloud.util.PKCoderUtil;
import com.cloud.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author summer
 */
public class LoginUtils {
    public static final String SESS_COOKIE_NAME;
    public static final String COOKIE_NAME_TRACE = "_gwtc_";
    public static final String ERROR_PASSORNAME = "passOrName";
    public static final String ERROR_USERNAME = "username";
    public static final String ERROR_PASSWORD = "password";
    public static final String ERROR_CAPTCHA = "captcha";
    public static final String ERROR_REJECTED = "rejected";
    public static final String ERROR_LOGOUT = "logout";
    public static final String ERROR_IPCHANGE = "ipChange";
    public static final String KEY_TIMEOUT_ = "TIMEOUT_";
    private static String encKey;

    static {
        SESS_COOKIE_NAME = Config.SESSION_COOKIE_NAME;
        encKey = "skTeis@2";
    }

    public LoginUtils() {
    }

    public static String getTraceId(String memberid) {
        return PKCoderUtil.encryptString(memberid, encKey);
    }

    public static String getUserIdByTraceId(String traceId) {
        return StringUtil.isHexDataStr(traceId) ? PKCoderUtil.decryptString(traceId, encKey) : null;
    }

    public static String getUserIdByFullTrace(HttpServletRequest request) {
        String track = BaseWebUtils.getCookieValue(request, "_gwtc_");
        if (StringUtils.isBlank(track)) {
            return null;
        } else {
            String userId = getUserIdByTraceId(StringUtils.split(track, ".")[0]);
            return userId;
        }
    }

    public static String[] getTracePair(HttpServletRequest request) {
        String track = BaseWebUtils.getCookieValue(request, "_gwtc_");
        if (StringUtils.isBlank(track)) {
            return null;
        } else {
            String[] tracePair = StringUtils.split(track, "@");
            return tracePair;
        }
    }

    public static boolean isValidSessid(String ukey) {
        if (StringUtils.length(ukey) != 32) {
            return isMemberEncode(ukey);
        } else {
            String random = ukey.substring(0, 24);
            String mykey = StringUtil.md5(random + "testKeyMemberLogon", 8);
            return mykey.equals(ukey.substring(24));
        }
    }

    private static boolean isMemberEncode(String memberEncode) {
        if (StringUtils.isBlank(memberEncode)) {
            return false;
        } else {
            String[] encodes;
            if (StringUtils.contains(memberEncode, ",")) {
                encodes = memberEncode.split(",");
                if (StringUtils.equals(encodes[0], encodes[1])) {
                    memberEncode = encodes[0];
                }
            }

            encodes = memberEncode.split("@");
            return encodes.length > 1;
        }
    }

    public static void removeLogonUkey(HttpServletResponse response) {
        Cookie cookie = new Cookie(SESS_COOKIE_NAME, (String) null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getSessid(HttpServletRequest request) {
        String value = BaseWebUtils.getCookieValue(request, SESS_COOKIE_NAME);
        return value;
    }

    public static String getRepeatKey(String usertype, String username) {
        return usertype + "__" + username;
    }

    public static void setLogonTrace(Long userid, HttpServletRequest request, HttpServletResponse response) {
        String traceUkey = BaseWebUtils.getCookieValue(request, "_gwtc_");
        setLogonTrace(userid, response, traceUkey);
    }

    private static void setLogonTrace(Long userid, HttpServletResponse response, String traceUkey) {
        if (StringUtils.isBlank(traceUkey)) {
            traceUkey = getTraceUkey();
        } else {
            int index = StringUtils.indexOf(traceUkey, '@');
            if (index > 0) {
                traceUkey = traceUkey.substring(0, index);
            }
        }

        if (userid != null) {
            traceUkey = traceUkey + "@" + getTraceId("" + userid);
        }

        Cookie cookie = new Cookie("_gwtc_", traceUkey);
        int duration = 2592000;
        cookie.setMaxAge(duration);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void setTraceIfNotExists(HttpServletRequest request, HttpServletResponse response) {
        String traceUkey = BaseWebUtils.getCookieValue(request, "_gwtc_");
        if (StringUtils.isBlank(traceUkey)) {
            setLogonTrace((Long) null, (HttpServletResponse) response, (String) traceUkey);
        }

    }

    private static String getTraceUkey() {
        String t = String.valueOf(System.currentTimeMillis());
        String r = "_" + StringUtil.getRandomString(4);
        String v = StringUtil.md5WithKey(t, 4);
        return t + r + "_" + v;
    }

    public static String getCacheUkey(String sessid) {
        return StringUtil.md5(sessid);
    }
}
