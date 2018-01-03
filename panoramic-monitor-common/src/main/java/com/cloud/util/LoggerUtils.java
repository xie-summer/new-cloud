package com.cloud.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author summer
 */
public class LoggerUtils {
	private static boolean notUseJson = false;
	static{
		notUseJson = new File("/notUseJson.txt").exists();
	}
	private static final String tracePackage = "com.panoramic";
	private static int singleMax = 0;			//最大异常次数
	private static AtomicInteger criticalCount = new AtomicInteger();		//关键异常
	private static String singleMaxName = "";	//最大异常名称
	public static String getSingleMaxName() {
		return singleMaxName;
	}
	public static int getSingleMax() {
		return singleMax;
	}
	private static Map<String/*exception or method*/, AtomicInteger> exceptionCount = new ConcurrentHashMap<String, AtomicInteger>();
	private static Long exceptionTimefrom = System.currentTimeMillis();
	private static Set<String> criticalException = new TreeSet<String>(Arrays.asList(
			"com.alibaba.dubbo.rpc.ProviderException",//没有服务提供者
			"com.mongodb.MongoException",
			"java.lang.ArrayIndexOutOfBoundsException",
			"java.net.UnknownHostException",
			"org.springframework.dao.DataAccessResourceFailureException",
			"org.springframework.dao.RecoverableDataAccessException",
			"org.springframework.jdbc.BadSqlGrammarException",
			"org.springframework.jms.UncategorizedJmsException",
			"java.lang.StackOverflowError",
			"java.lang.NoClassDefFoundError"));
	public static Long getExceptionTimefrom() {
		return exceptionTimefrom;
	}
	public static int getCriticalExceptionCount(){
		int ret = criticalCount.get();
		return ret;
	}
	public static void incrementCount(Throwable e, String traceMethod){
		if(e!=null){
			String name = e.getClass().getName();
			if(criticalException.contains(name)){
				criticalCount.incrementAndGet();
			}
			if(StringUtils.isNotBlank(traceMethod)){
				name += "@" + traceMethod;
			}
			AtomicInteger counter = exceptionCount.get(name);
			if(counter==null){
				counter = new AtomicInteger(1);
				exceptionCount.put(name, counter);
			}else{
				int max = counter.incrementAndGet();
				if(max > singleMax){
					singleMaxName = name;
					singleMax = max;
				}
			}
		}
	}

	public static Map<String, Integer> getExceptionCountMap(){
		return getCountMap(exceptionCount);
	}
	public static Map<String, Integer> resetExceptionCount(){
		Map<String, AtomicInteger> cur = exceptionCount;
		exceptionCount = Maps.newConcurrentMap();
		criticalCount.set(0);
		exceptionTimefrom = System.currentTimeMillis();
		singleMax = 0;
		singleMaxName = "";
		return getCountMap(cur);
	}
	private static Map<String, Integer> getCountMap(Map<String, AtomicInteger> countMap){
		Map<String, Integer> result = Maps.newHashMap();
		for(Map.Entry<String, AtomicInteger> entry: countMap.entrySet()){
			result.put(entry.getKey(), entry.getValue().get());
		}
		return result;
	}
	public static TLogger getLogger(Class clazz){
		Logger logger = LoggerFactory.getLogger(clazz);
		if(notUseJson) {
            return new SimpleLogger(logger);
        }
		return new JsonLogger(logger, IpConfig.getServerip(), null);
	}
	public static TLogger getLogger(Class clazz, String serverIp, String systemId) {
		Logger logger = LoggerFactory.getLogger(clazz);
		if(notUseJson) {
            return new SimpleLogger(logger);
        }
		return new JsonLogger(logger, serverIp, systemId);
	}

	public static TLogger getLogger(String name, String serverIp, String systemId){
		Logger logger = LoggerFactory.getLogger(name);
		if(notUseJson) {
            return new SimpleLogger(logger);
        }
		return new JsonLogger(logger, serverIp, systemId);
	}

	public static String getExceptionTrace(Throwable e) {
		return getExceptionTrace(e, 100);
	}

	public static String getExceptionTrace(Throwable e, int rows) {
		StringBuffer result = new StringBuffer(e.getClass().getCanonicalName() + ": " + e.getMessage());
		rows --;
		String tracedMethod = getExceptionTrace(result, e, rows);
		incrementCount(e, tracedMethod);
		return result.toString();
	}
	private static String getExceptionTrace(StringBuffer result, Throwable e, int rows){
		result.append(e);
		StackTraceElement[] traces = e.getStackTrace();
		String tmp = "", tracedMethod = null;
		for (int i=0;i< traces.length && rows>=0; i++) {
			tmp = traces[i].toString();
			if(tracedMethod==null && StringUtils.contains(tmp, tracePackage)){
				tracedMethod = tmp;
			}
			result.append("\n\tat " + tmp);
			rows --;
		}
		if (rows > 0) {
			Throwable ourCause = e.getCause();
			if (ourCause != null){
				result.append(ourCause);
				result.append("\nCaused by");
				rows --;
				if(rows>0){
					String trace = getExceptionTrace(result, ourCause, rows);
					if(tracedMethod == null) {
						tracedMethod = trace;
					}
				}
			}
		}
		return tracedMethod;
	}
//	public static void main(String[] args) throws IOException {
//		File file = new File("/notUseJson.txt");
//		if(!file.exists()){
//			file.createNewFile();
//			System.out.println("notUseJson File create:" + file.exists());
//		}
//	}
}