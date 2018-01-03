package com.cloud.util;

import java.util.List;
import java.util.Random;
/**
 * @author summer
 */
public class RandomUtils {

	/**
	 * 返回随机整数
	 * <p>封装java.util.Random
	 * @param n 范围值
	 * @return n<=1,返回0;其他返回范围0~(n-1)内的随机整数
	 */
	public static int randomInt(int n){
		if(n <= 1){
			return 0;
		}
		return new Random().nextInt(n);
	}

	/**
	 * 返回随机整数
	 * <p>封装org.apache.commons.lang.math.RandomUtils
	 * <p>在万次以上的调用上，时间优于{@link #randomInt(int)}
	 * @param n 范围值
	 * @return n<=1,返回0;其他返回范围0~(n-1)内的随机整数
	 */
	public static int randomIntApache(int n){
		if(n <= 1){
			return 0;
		}
		return org.apache.commons.lang.math.RandomUtils.nextInt(n);
	}

	/**
	 * 随机返回List中的值，采用{@link #randomInt(int)}
	 * @param list
	 * @return 如果list为null或空，则返回null
	 */
	public static <T> T getRandomFromList(List<T> list){
		if(list == null || list.isEmpty()){
			return null;
		}
		return list.get(randomInt(list.size()));
	}

	/**
	 * 随机返回List中的值，采用{@link #randomIntApache(int)}
	 * @param <T>
	 * @param list
	 * @return 如果list为null或空，则返回null
	 */
	public static <T> T getRandomFromListApache(List<T> list){
		if(list == null || list.isEmpty()){
			return null;
		}
		return list.get(randomInt(list.size()));
	}
}
