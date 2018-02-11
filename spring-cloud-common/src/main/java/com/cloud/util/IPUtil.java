package com.cloud.util;

import com.google.common.collect.Maps;
import org.apache.commons.collections.map.UnmodifiableMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author summer
 */
public class IPUtil {
    public final static IpData ipData = new IpData();
    private final static transient TLogger DB_LOGGER = LoggerUtils.getLogger(IPUtil.class);
    private static Map<String/*code*/, String/*name*/> provinceMap;
    private static Map<String/*provinceCode*/, Map<String/*citycode*/, String/*cityname*/>> proCityMap;
    private static Map<String/*citycode*/, String/*cityname*/> cityMap = Maps.newHashMap();
    private static Map<String/*citycode*/, String/*provincecode*/> city2Pro = Maps.newHashMap();
    private static AtomicBoolean init = new AtomicBoolean(false);

    static {
        init();
    }

    /**
     * @param ip
     * @return procode, citycode, cityname
     */
    public static String[] findProAndCityByIp(String ip) {
        if (StringUtils.equals("::1", ip)) {
            ip = "127.0.0.1";
        }
        try {
            String address = getAddress(ip);
            if (StringUtils.isNotBlank(address)) {
                for (Map.Entry<String, String> pro : provinceMap.entrySet()) {
                    if (StringUtils.contains(address, pro.getValue())) {
                        Map<String, String> city = proCityMap.get(pro.getKey());
                        for (Map.Entry<String, String> gewaCity : city.entrySet()) {
                            if (StringUtils.contains(address, gewaCity.getValue())) {
                                return new String[]{pro.getKey(), gewaCity.getKey(), gewaCity.getValue()};
                            }
                        }
                        return new String[]{pro.getKey(), "", ""};
                    }
                }
            }
        } catch (Exception e) {
            DB_LOGGER.error("获取城市代码错误", e);
        }
        return new String[]{"", "", ""};
    }

    public static String getCitynameByCode(String citycode) {
        return cityMap.get(citycode);
    }

    private static void init() {
        if (init.get()) {
            return;
        }
        boolean first = init.compareAndSet(false, true);
        if (!first) {
            return;
        }
        //1)ip
        try (
                Reader reader = new BufferedReader(new InputStreamReader(IPUtil.class.getClassLoader().getResourceAsStream("ipdata.txt"), "utf-8"));
        ) {
            List<String> lines = IOUtils.readLines(reader);
            init(lines);
        } catch (Exception e) {
            throw new IllegalArgumentException("IPData ERROR!!!", e);
        }
        //2)province
        try (
                Reader reader = new BufferedReader(new InputStreamReader(IPUtil.class.getClassLoader().getResourceAsStream("province.txt"), "utf-8"));
        ) {
            List<String> provinceList = IOUtils.readLines(reader);
            Map<String, String> map = Maps.newHashMap();
            for (String province : provinceList) {
                String[] pair = StringUtils.split(province, "\t");
                if (pair.length == 2) {
                    map.put(StringUtils.trim(pair[0]), StringUtils.trim(pair[1]));
                }
            }
            provinceMap = UnmodifiableMap.decorate(map);
        } catch (Exception e) {
            throw new IllegalArgumentException("Province data ERROR!!!", e);
        }
        //3)city
        try (
                Reader reader = new BufferedReader(new InputStreamReader(IPUtil.class.getClassLoader().getResourceAsStream("city.txt"), "utf-8"));
        ) {
            List<String> cityList = IOUtils.readLines(reader);
            Map<String/*provinceCode*/, Map<String/*citycode*/, String/*cityname*/>> cmap = Maps.newHashMap();

            for (String city : cityList) {
                String[] pair/*pcode,ccode,cname*/ = StringUtils.split(city, "\t");
                if (pair.length == 3) {
                    Map<String, String> row = cmap.get(pair[0]);
                    if (row == null) {
                        row = Maps.newHashMap();
                        cmap.put(pair[0], row);
                    }
                    row.put(pair[1], pair[2]);
                    cityMap.put(pair[1], pair[2]);
                    city2Pro.put(pair[1], pair[0]);
                }
            }
            proCityMap = UnmodifiableMap.decorate(cmap);
        } catch (Exception e) {
            throw new IllegalArgumentException("Province data ERROR!!!", e);
        }

    }

    private static void init(List<String> lines) {
        ipData.ipList = new long[lines.size() + 4];
        int i = 2;
        int success = 0, error = 0;
        Long ipn1, ipn2;
        for (String line : lines) {
            try {
                String[] ipdata = StringUtils.trim(line).split("[ ]+");
                ipn1 = getIpNum(ipdata[0]);
                ipn2 = getIpNum(ipdata[1]);
                ipData.ipList[i] = ipn1;
                i++;
                ipData.pairMap.put(ipn1, ipn2);
                // 哨兵
                ipData.addressMap.put(ipn1, ipdata[2] + (ipdata.length > 3 ? "  " + ipdata[3] : ""));
                success++;
            } catch (Exception e) {
                error++;
                DB_LOGGER.warn("RowError:" + line + ", LineNo:" + (success + error));
            }
        }
        ipData.ipList[0] = Long.MIN_VALUE;
        ipData.ipList[1] = Long.MIN_VALUE + 1;
        ipData.ipList[i] = Long.MAX_VALUE - 1;
        ipData.ipList[i + 1] = Long.MAX_VALUE;
        Arrays.sort(ipData.ipList);
        ipData.ipList[1] = ipData.ipList[2] - 1;
        ipData.ipList[i] = ipData.pairMap.get(ipData.ipList[i - 1]) + 1;
        DB_LOGGER.warn("Init IP Data, total count:" + lines.size() + ",success:" + success + ",error:" + error);
    }

    public static String getAddress(String ip) {
        long ipNum = getIpNum(ip);
        int idx = findNear(ipNum);
        Long ip1 = ipData.ipList[idx];
        Long ip2 = ipData.pairMap.get(ip1);
        String find = null;
        if (ip1 == null || ip2 == null) {
            DB_LOGGER.warn("INVALIDIP:" + ip);
            return find;
        }
        if (ipNum >= ip1 && ipNum <= ip2) {
            find = ipData.addressMap.get(ip1);
        }
        return find;
    }

    private static Long getIpNum(String ip) {
        String[] ip1 = StringUtils.split(StringUtils.trim(ip), ".");
        if (ip1 != null && ip1.length > 3) {
            return Long.parseLong(ip1[0]) * 256 * 256 * 256 + Long.parseLong(ip1[1]) * 256 * 256 + Long.parseLong(ip1[2]) * 256 + Long.parseLong(ip1[3]);
        } else {
            DB_LOGGER.warn("INVALIDIP:" + ip);
        }
        return 0L;
    }

    private static int findNear(long ipNum) {
        int start = 0, end = ipData.ipList.length, mid = -1;
        while (start != end && start + 1 != end) {
            mid = (start + end) / 2;
            if (ipData.ipList[mid] == ipNum) {
                return mid;
            }
            if (ipData.ipList[mid + 1] == ipNum) {
                return mid + 1;
            }
            if (ipNum > ipData.ipList[mid] && ipNum < ipData.ipList[mid + 1]) {
                return mid;
            }
            if (ipNum > ipData.ipList[mid]) {
                start = mid;
            } else {
                end = mid;
            }
        }
        return start;
    }

    public static class IpData implements Serializable {
        private static final long serialVersionUID = 1L;
        private long[] ipList = new long[]{};
        private Map<Long, Long> pairMap = new HashMap<Long, Long>();
        private Map<Long, String> addressMap = new HashMap<Long, String>();
    }
}
