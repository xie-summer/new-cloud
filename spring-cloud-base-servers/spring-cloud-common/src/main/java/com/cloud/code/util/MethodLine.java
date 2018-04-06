package com.cloud.code.util;

import org.apache.commons.lang.StringUtils;
/**
 * @author summer
 */
public class MethodLine{
	public String method;
	public Integer lineNo;
	public String callname;
	public String clazz;
	public boolean error;
	public MethodLine(String clazz, String method, Integer lineNo){
		this.clazz = StringUtils.substringAfterLast(clazz, ".");
		this.method = method;
		this.lineNo = lineNo;
	}
	@Override
	public String toString(){
		return callname + "," + "line:" + lineNo;
	}
	public String full(){
		return clazz + ":" + method + ",line:" + lineNo;
	}
}
