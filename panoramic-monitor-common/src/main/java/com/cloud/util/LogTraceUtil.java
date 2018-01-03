package com.cloud.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 用来做系统出错定位跟踪
 *
 * @author gebiao(ge.biao@gewara.com)
 * @since Jan 18, 2014 3:12:29 PM
 */
public class LogTraceUtil {
    private final static TLogger DB_LOGGER = LoggerUtils.getLogger(LogTraceUtil.class);
    private static List<LogTrace> traceList = Lists.newArrayList();

    public static void addTrace(LogTrace trace) {
        traceList.add(trace);
    }

    public static List<String> getTraceInfo() {
        List<String> result = Lists.newArrayList();
        for (LogTrace trace : traceList) {
            try {
                String ts = trace.getTrace();
                if (StringUtils.isNotBlank(ts)) {
                    result.add(ts);
                }
            } catch (Throwable e) {
                DB_LOGGER.error("", e);
            }
        }
        return result;
    }

    public static interface LogTrace {
        String getTrace();
    }
}
