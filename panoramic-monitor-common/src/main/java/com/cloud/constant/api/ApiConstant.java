package com.cloud.constant.api;

import com.google.common.collect.Maps;
import org.apache.commons.collections.map.UnmodifiableMap;

import java.util.Map;

/**
 * @author summer
 * 0000	成功
 * <p>
 * 5000	用户不存在
 * 5001	用户未登录
 * 5002	用户无权限
 * 5003	不能重复操作
 * <p>
 * 9999  未知错误：其他未分类的错误
 */
public abstract class ApiConstant {
    public static final String CODE_SUCCESS = "0000";
    public static final String CODE_OPI_NOT_EXISTS = "1001";
    public static final String CODE_OPI_CLOSED = "1002";
    public static final String CODE_OPI_UNSYNCH = "1003";

    public static final String CODE_CONNECTION_ERROR = "2001";
    public static final String CODE_SEAT_POS_ERROR = "2002";
    public static final String CODE_SEAT_OCCUPIED = "2003";
    public static final String CODE_SEAT_NUM_ERROR = "2004";
    public static final String CODE_SEAT_LIMITED = "2005";
    public static final String CODE_SEAT_BREAK_RULE = "2006";
    public static final String CODE_SEAT_LOCK_ERROR_CINEMA = "2010";
    public static final String CODE_CCTO_ERROR = "2011";
    public static final String CODE_TC_ERROR = "2012";
    public static final String CODE_SEAT_RELEASED = "2013";
    public static final String CODE_SYNCH_DATA = "2098";
    public static final String CODE_SEAT_LOCK_ERROR = "2099";

    public static final String CODE_PARTNER_NOT_EXISTS = "4001";
    public static final String CODE_PARTNER_NORIGHTS = "4002";
    public static final String CODE_SIGN_ERROR = "4003";
    public static final String CODE_PARAM_ERROR = "4004";
    public static final String CODE_DATA_ERROR = "4005";
    public static final String CODE_PAY_ERROR = "4006";

    public static final String CODE_WEIBO_EXPRIES = "4100";
    public static final String CODE_UNBIND_MOBILE = "4101";

    public static final String CODE_MEMBER_NOT_EXISTS = "5000";
    public static final String CODE_NOTLOGIN = "5001";
    public static final String CODE_USER_NORIGHTS = "5002";
    public static final String CODE_REPEAT_OPERATION = "5003";
    public static final String CODE_NOT_EXISTS = "5004";

    public static final String CODE_PAYPASS_ERROR = "6001";

    public static final String CODE_UNKNOWN_ERROR = "9999";

    public static final Map<String, String> ORDER_STATUS_MAP;

    static {
        Map<String, String> tmp = Maps.newHashMap();
        ORDER_STATUS_MAP = UnmodifiableMap.decorate(tmp);
    }

    public static Map<String, String> getOrderStatusMap() {
        return ORDER_STATUS_MAP;
    }

    public static String getMappedOrderStatus(String status) {
        return ORDER_STATUS_MAP.get(status);
    }
}
