package com.cloud.util;

import org.apache.commons.lang.StringUtils;
/**
 * @author summer
 */
public class SystemUtils {
	public static final String getShortHostname(String hostname){
		int idx = StringUtils.indexOf(hostname, '.');
		if(idx>0){
			return StringUtils.substring(hostname, 0, idx);
		}
		return hostname;
	}
	/**
	 * 防止文件名有".."这种上级目录及空格
	 * @param filename
	 * @return
	 */
	public static boolean isNormalFile(String filename){
		return StringUtils.isNotBlank(filename) && !StringUtils.contains(filename, "..");
	}
}
