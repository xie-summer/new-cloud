package com.cloud.untrans;
/**
 * @author summer
 */
public interface CacheTools {
	/**
	 * @param regionName
	 * @param key
	 * @return
	 */
	Object get(String regionName, String key);
	/**
	 * 
	 * @param regionName
	 * @param key
	 * @param value
	 */
	void set(String regionName, String key, Object value);

	/**
	 * @param regionName
	 * @param key
	 */
	void remove(String regionName, String key);

	/**
	 * @return
	 */
	boolean isLocal();
}
