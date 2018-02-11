package com.cloud.untrans;


import com.cloud.util.Gcache;
import com.cloud.util.StringUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ge.biao
 * 使用本地缓存做一层代理，使用集中缓存做数据预热
 */
public class LocalCacheTools implements CacheTools{
    private Gcache<String/*key*/, Object> cachedUkeyMap;
    private CacheService cacheService;
    private AtomicInteger remoteHit = new AtomicInteger(0);
    public LocalCacheTools(int maxnum, CacheService cacheService){
        cachedUkeyMap = new Gcache<String, Object>(maxnum);
        this.cacheService = cacheService;
    }

    private String getRealKey(String regionName, String key){
        String result = regionName + key;
        if(result.length()>128){
            result = "md5" + StringUtil.md5(key);
        }
        return result;
    }


    @Override
    public Object get(String regionName, String key) {
        String realKey = getRealKey(regionName, key);
        Object result = cachedUkeyMap.getIfPresent(realKey);
        if(result == null){
            result = cacheService.get(regionName, key);
            if(result!=null){
                remoteHit.incrementAndGet();
                cachedUkeyMap.put(realKey, result);
            }
        }
        return result;
    }

    @Override
    public void set(String regionName, String key, Object value) {
        cachedUkeyMap.put(getRealKey(regionName, key), value);
        cacheService.set(regionName, key, value);
    }

    @Override
    public void remove(String regionName, String key) {
        cachedUkeyMap.invalidate(getRealKey(regionName, key));
        cacheService.remove(regionName, key);
    }

    @Override
    public boolean isLocal() {
        return true;
    }

    public int getRemoteHit() {
        return remoteHit.get();
    }

}

