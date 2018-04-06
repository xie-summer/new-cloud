package com.cloud.util;

import com.cloud.support.LocalCachable;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
/**
 * @author summer
 */
public class Gcache<K, V> implements Cache<K, V>, Serializable{

	private static final long serialVersionUID = -231460062536441961L;
	private long starttime = System.currentTimeMillis();
	private Cache<K, V> cache;
	private CacheStats startCacheStats;

	public Gcache(long maxinumSize){
		int concurrency = Math.min(64, Math.max(16, (int)(maxinumSize/1000)));
		this.cache = CacheBuilder.newBuilder().maximumSize(maxinumSize).concurrencyLevel(concurrency).recordStats().build();
		this.startCacheStats = cache.stats();
	}
	/**
	 * 某个键值对被创建或值被替换后多少时间移除
	 * @param maxinumSize
	 * @param duration
	 * @param unit
	 */
	public Gcache(long maxinumSize, long duration, TimeUnit unit){
		int concurrency = Math.min(64, Math.max(16, (int)(maxinumSize/1000)));
		this.cache = CacheBuilder.newBuilder().maximumSize(maxinumSize).concurrencyLevel(concurrency).expireAfterWrite(duration, unit).recordStats().build();
		this.startCacheStats = cache.stats();
	}

	@Override
	public V getIfPresent(Object key) {
		return cache.getIfPresent(key);
	}

	@Override
	public V get(K key, Callable<? extends V> valueLoader)  throws ExecutionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ImmutableMap getAllPresent(Iterable keys) {
		return cache.getAllPresent(keys);
	}

	@Override
	public void put(K key, V value) {
		//TODO instanceof BaseObject and not instanceof LocalCachable ,invalid!!
		if(value instanceof LocalCachable){
			//不允许数据库变更
			((LocalCachable)value).fix2Cache();
		}
		cache.put(key, value);

	}

	@Override
	public void putAll(Map<? extends K,? extends V> m) {
		for(Entry<? extends K, ? extends V> entry: m.entrySet()){
			//不允许数据库变更
			if(entry.getValue()!=null && entry.getValue() instanceof LocalCachable){
				((LocalCachable)entry.getValue()).fix2Cache();
			}
		}
		cache.putAll(m);
	}

	@Override
	public void invalidate(Object key) {
		cache.invalidate(key);
	}

	@Override
	public void invalidateAll(Iterable<?> keys) {
		cache.invalidateAll(keys);
	}

	@Override
	public void invalidateAll() {
		cache.invalidateAll();
	}

	@Override
	public long size() {
		return cache.size();
	}

	@Override
	public CacheStats stats() {
		return cache.stats();
	}

	@Override
	public ConcurrentMap<K, V> asMap() {
		return cache.asMap();
	}

	@Override
	public void cleanUp() {
		cache.cleanUp();
	}

	public long getStarttime() {
		return starttime;
	}

	public CacheStats getStartCacheStats(){
		return startCacheStats;
	}

	public void resetCacheStats(CacheStats cacheStats) {
		startCacheStats = cacheStats;
		starttime = System.currentTimeMillis();
	}
}
