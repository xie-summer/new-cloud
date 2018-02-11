package com.cloud.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.*;
/**
 * @author summer
 */
public class ChangeEntry {
	public ChangeEntry(){
	}
	public ChangeEntry(Map<String, ?> oldMap){
		this.oldMap = oldMap;
	}
	public ChangeEntry(Object oldObj){
		setOld(oldObj);
	}
	private Map<String, ?> oldMap;
	public void setOld(Object oldObj){
		if(oldObj!=null) {
			oldMap = BeanUtil.getBeanMap(oldObj);
		}
		if(oldMap==null) {
            oldMap = Maps.newHashMap();
        }
	}
	public Map<String, String> getChangeMap(Object newObj){
		Map<String, ?> tmpMap = new HashMap<String, Object>(oldMap);
		Map<String, ?> newMap = BeanUtil.getBeanMap(newObj);
		if(newMap ==null) {
            newMap = Maps.newHashMap();
        }
		Map<String, String> changeMap =Maps.newHashMap();
		List<String> keySet = new ArrayList<String>();
		keySet.addAll(tmpMap.keySet());
		keySet.addAll(newMap.keySet());
		for(String key: keySet){
			Object oldvalue = tmpMap.remove(key);
			Object newvalue = newMap.remove(key);
			String ov = getStringValue(oldvalue);
			String nv = getStringValue(newvalue);
			if(!StringUtils.equals(ov, nv)){
				changeMap.put(key, ov + "==>" + nv);
			}
		}
		return changeMap;
	}
	public String getChangeMap(Map<String, String> changeMap){
		if(changeMap.isEmpty()) {
            return "";
        }
		String change = changeMap.toString();
		return change;
	}
	public String getStringValue(Object value){
		if(value==null) {
            return "";
        }
		if(value instanceof String) {
            return (String) value;
        }
		if(ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
            return "" + value;
        }
		if(value instanceof Timestamp) {
            return DateUtil.formatTimestamp((Timestamp) value);
        }
		if(value instanceof Date) {
            return DateUtil.formatDate((Date) value);
        }
		return ""+value;
	}
}
