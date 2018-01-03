package com.cloud.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author summer
 */
public class MobileAreaUtil {
	private static Map<String, String> mobileAreaMap = new HashMap<String, String>();
	private final static transient TLogger DB_LOGGER = LoggerUtils.getLogger(MobileAreaUtil.class);
	static{
		init();
	}
	private static void init() {
		try{
			Reader reader = new BufferedReader(new InputStreamReader(MobileAreaUtil.class.getClassLoader().getResourceAsStream("mobile_area.txt"), "utf-8"));
			List<String> lines = IOUtils.readLines(reader);
			for (String line : lines) {
				String[] tmp = StringUtils.split(line, "\t");
				mobileAreaMap.put(tmp[0], tmp[1] + "," + tmp[2]);
			}
		}catch(Exception e){
			DB_LOGGER.warn("MobileAreaUtil init error:" + e.getMessage());
		}
		DB_LOGGER.warn("MobileAreaUtil init size:" + mobileAreaMap.size());
	}
	public static String getMobileCitycode(String mobile){
		if(!ValidateUtil.isMobile(mobile)){
			return "";
		}
		String smobile = mobile.substring(0, 7);
		String res = mobileAreaMap.get(smobile);
		if(StringUtils.isBlank(res)){
			DB_LOGGER.warn("unfind mobile area:" + mobile);
			return "";
		}
		return StringUtils.split(res, ",")[0];
	}
	public static String getMobileProcode(String mobile){
		if(!ValidateUtil.isMobile(mobile)){
			return "";
		}
		String smobile = mobile.substring(0, 7);
		String res = mobileAreaMap.get(smobile);
		if(StringUtils.isBlank(res)){
			DB_LOGGER.warn("unfind mobile area:" + mobile);
			return "";
		}
		return StringUtils.split(res, ",")[1];
	}
}
