package com.cloud.support;

/**
 * @author summer
 */
public interface LocalCachable {
    /**
     * 当进入本地缓存时，再次读出数据，应该返回true，防止本地缓存被保存
     *
     * @return
     */
    boolean fromCache();

    /**
     * 将此对象设置为本地缓存
     */
    void fix2Cache();

    /**
     * 判断数据是否要缓存，比如针对历史数据不缓存
     *
     * @return
     */
    boolean cachable();

}
