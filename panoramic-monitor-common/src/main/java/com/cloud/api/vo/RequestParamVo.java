package com.cloud.api.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cloud.util.DateUtil;

/**
 * @author  summer
 * 只用于后台管理类dubbo接口数据封装
 */
public class RequestParamVo implements Serializable{
	private static final long serialVersionUID = 118918163520189721L;
	private Map<String, String> params;
	private String reqUri;		//请求URI
	private String systemid;	//系统ID
	private Long logonUserId;	//登录用户ID
	private String sessionid;	//登录用户sessionid
	private String remoteIp;
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public String getReqUri() {
		return reqUri;
	}
	public void setReqUri(String reqUri) {
		this.reqUri = reqUri;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public Long getLogonUserId() {
		return logonUserId;
	}
	public void setLogonUserId(Long logonUserId) {
		this.logonUserId = logonUserId;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Long getLong(String key){
		String result = getString(key);
		if(StringUtils.isBlank(result)) {
            return null;
        }
		return Long.parseLong(result);
	}

	public Integer getInteger(String key){
		String result = getString(key);
		if(StringUtils.isBlank(result)) {
            return null;
        }
		return Integer.parseInt(result);
	}

	public Timestamp getTimestamp(String key){
		String result = getString(key);
		if(StringUtils.isBlank(result)) {
            return null;
        }
		return DateUtil.parseTimestamp(result);
	}

	public Date getDate(String key){
		String result = getString(key);
		if(StringUtils.isBlank(result)) {
            return null;
        }
		return DateUtil.parseDate(result);
	}

	public String getString(String key){
		if(params == null) {
            return null;
        }
		String result = params.get(key);
		return result;
	}
}
