package com.risk.warning.constant;

import org.apache.commons.collections.map.UnmodifiableMap;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author summer
 */
public abstract class CityConstant {

    public static final String PROVINCEID_SH = "310000";//上海市
    public static final String CITYCODE_SH = "310000";//上海

    public static final String PROVINCEID_TJ = "120000";//天津市
    public static final String CITYCODE_TJ = "120000";//天津

    public static final String PROVINCEID_JS = "320000";//江苏省
    public static final String CITYCODE_WX = "320200";//无锡
    public static final String CITYCODE_SZ = "320500";//苏州
    public static final String CITYCODE_NJ = "320100";//南京
    public static final String CITYCODE_NT = "320600";//南通

    public static final String PROVINCEID_ZJ = "330000";//浙江省
    public static final String CITYCODE_HZ = "330100";//杭州

    public static final String PROVINCEID_SD = "370000";//山东省
    public static final String CITYCODE_QD = "370200";//青岛

    public static final String PROVINCEID_HN = "410000";//河南省
    public static final String CITYCODE_ZZ = "410100";//郑州

    public static final String PROVINCEID_FJ = "350000";//福建省
    public static final String CITYCODE_XM = "350200";//厦门
    public static final String CITYCODE_FJZZ = "350600";//漳州

    public static final String PROVINCEID_GZ = "520000";//贵州省
    public static final String CITYCODE_GY = "520100";//贵阳

    public static final String PROVINCEID_GD = "440000";//广东省
    public static final String CITYCODE_FS = "440600";//佛山

    public static final String PROVINCEID_SC = "510000";//四川省
    public static final String CITYCODE_CD = "510100";//成都

    public static final List<String> ALLCITY = Arrays.asList(CITYCODE_SH, CITYCODE_WX, CITYCODE_HZ,
            CITYCODE_TJ, CITYCODE_SZ, CITYCODE_NJ, CITYCODE_NT, CITYCODE_QD,
            CITYCODE_ZZ, CITYCODE_XM, CITYCODE_GY, CITYCODE_FS);
    public static Map<String, String> CITYMAP = new LinkedHashMap<String, String>();
    public static Map<String, String> OPENCITYMAP = new LinkedHashMap<String, String>();

    static {
        Map map = new LinkedHashMap<String, String>();
        map.put(CITYCODE_SH, "上海");
        map.put(CITYCODE_WX, "无锡");
        map.put(CITYCODE_HZ, "杭州");
        map.put(CITYCODE_TJ, "天津");
        map.put(CITYCODE_SZ, "苏州");
        map.put(CITYCODE_NJ, "南京");
        map.put(CITYCODE_NT, "南通");
        map.put(CITYCODE_QD, "青岛");
        map.put(CITYCODE_ZZ, "郑州");
        map.put(CITYCODE_XM, "厦门");
        map.put(CITYCODE_GY, "贵阳");
        map.put(CITYCODE_FS, "佛山");
        map.put(CITYCODE_CD, "成都");
        map.put(CITYCODE_FJZZ, "漳州");
        CITYMAP = UnmodifiableMap.decorate(map);
        OPENCITYMAP = UnmodifiableMap.decorate(map);
    }

}
