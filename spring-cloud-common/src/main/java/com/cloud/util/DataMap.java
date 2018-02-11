package com.cloud.util;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
/**
 * @author summer
 */
public class DataMap implements Map {
	private Map map;
	public DataMap(Map map){
		this.map = map;
	}
	public Long longValue(String key){
		Object value = map.get(key);
		if(value==null) {
            return null;
        }
		return Long.parseLong(value.toString());
	}
	public Integer intValue(String key){
		Object value = map.get(key);
		if(value==null) {
            return null;
        }
		return Integer.parseInt(value.toString());
	}
	public Double doubleValue(String key){
		Object value = map.get(key);
		if(value==null) {
            return null;
        }
		return Double.parseDouble(value.toString());
	}
	public Timestamp timestampValue(String key){
		Object value = map.get(key);
		if(value==null) {
            return null;
        }
		if(value instanceof Timestamp){
			return (Timestamp) value;
		}else{
			return DateUtil.parseTimestamp(""+value);
		}
	}
	public Date dateValue(String key){
		Object value = map.get(key);
		if(value==null) {
            return null;
        }
		if(value instanceof Date){
			return (Date) value;	
		}
		return DateUtil.parseDate(""+value); 
	}
	public String stringValue(String key){
		Object value = map.get(key);
		if(value==null) {
            return null;
        }
		return value.toString();
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@Override
	public int size() {
		return map.size();
	}
	@Override
	public boolean isEmpty() {
		return map==null || map.isEmpty();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	@Override
	public Object get(Object key) {
		return map.get(key);
	}
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}
	@Override
	public void putAll(Map m) {
		map.putAll(m);
	}
	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public Set keySet() {
		return map.keySet();
	}
	@Override
	public Collection values() {
		return map.values();
	}
	@Override
	public Set entrySet() {
		return map.entrySet();
	}
}
