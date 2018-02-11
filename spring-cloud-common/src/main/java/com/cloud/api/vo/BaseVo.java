package com.cloud.api.vo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author summer
 */
public abstract class BaseVo implements Serializable {
	private static final long serialVersionUID = -1291080555056535690L;
	private Map<String, String> attach;
	public abstract Serializable realId();

	@Override
	public final int hashCode() {
		return (realId() == null) ? 0 : realId().hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		BaseVo other = (BaseVo) obj;
		return !(this.realId() != null ? !(this.realId().equals(other.realId())) : (other.realId() != null));
	}

	public void addAttach(String key, String value){
		if(attach==null){
			attach = new LinkedHashMap<String, String>();
		}
		attach.put(key, value);
	}
	public void addAttach(Map<String, String> attachMap){
		if(attach==null){
			attach = new LinkedHashMap<String, String>();
		}
		for(Map.Entry<String, String> entry: attachMap.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			attach.put(key, value);
		}
	}
	public String getAttachByKey(String key){
		if(attach!=null) {
            return attach.get(key);
        }
		return null;
	}
	public Map<String, String> getAttach() {
		return attach;
	}
}