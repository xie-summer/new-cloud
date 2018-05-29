package com.weimob.mengdian.soa.kafka.utils;

/**
 * 执行耗时监控工具类
 * @Author chenwp
 * @Date 2017-07-10 14:53
 * @Company WeiMob
 * @Description
 */
public final class StopWatch {
    private long start;

    public StopWatch() {
        reset();
    }

    public void reset() {
        start = System.currentTimeMillis();
    }

    public long elapsedTime() {
        long end = System.currentTimeMillis();
        return end - start;
    }
}
