package com.cloud.api.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.cloud.util.BeanUtil;
import com.cloud.util.TLogger;
import com.cloud.util.LoggerUtils;
/**
 * @author summer
 */
public class VoMap<K, V> implements Map<K, V>, Serializable{
	private static final long serialVersionUID = -6947799462487517862L;
	private static TLogger DB_LOGGER = LoggerUtils.getLogger(VoMap.class);
	private Map<K, V> _underline;
	public VoMap() {
		_underline = new LinkedHashMap<K, V>(0);
	}
	public VoMap(int initialCapacity) {
		_underline = new LinkedHashMap<K, V>(initialCapacity);
	}

	public VoMap(int initialCapacity, float loadFactor) {
		_underline = new LinkedHashMap<K, V>(initialCapacity, loadFactor);
	}

	@Override
	public V put(K key, V value) {
		checkValue(value, 1);
		//if(c){
		return _underline.put(key, value);
		//}
		//return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Set<? extends K> keys = m.keySet();
		for(K key: keys){
			put(key, m.get(key));
		}
	}
	/**
	 * 只支持6层集合嵌套，防止互相引用死循环
	 * @param value
	 * @return
	 */
	private boolean checkValue(Object value, int deep){
		if(value==null) {
            return true;
        }
		if(deep >= 7){//最多6层嵌套
			return false;
		}
		//1)primary, primary array...
		if(isValid(value)){
			return true;
		}
		//2)collection:check every value
		if(value instanceof Collection){
			return checkCollection((Collection)value, deep+1);
		}else if(value instanceof Map){
			return checkMap((Map)value, deep+1);
		}else if(value.getClass().isArray()){//3)array:check every value
			Class<?> clazz = value.getClass().getComponentType();
			if(BeanUtil.isSimpleProperty(clazz) || BaseVo.class.isAssignableFrom(clazz)){
				return true;
			}
		}

		DB_LOGGER.warn("InvalidVoMapData:" + value);
		return false;
	}
	private boolean checkMap(Map value, int deep){
		if(deep >= 7){//最多6层嵌套
			return false;
		}
		Collection values = value.values();
		Iterator it = values.iterator();
		while(it.hasNext()){
			Object v = it.next();
			boolean c = checkValue(v, deep);
			if(!c) {
				DB_LOGGER.warn("InvalidVoMapData:" + v);
				return c;
			}
		}
		return true;
	}
	private boolean checkCollection(Collection value, int deep){
		if(deep >= 7){//最多6层嵌套
			return false;
		}
		Collection cv = (Collection) value;
		Iterator it = cv.iterator();
		while(it.hasNext()){
			Object v = it.next();
			boolean c = checkValue(v, deep);
			if(!c) {
				DB_LOGGER.warn("InvalidVoMapData:" + v);
				return c;
			}
		}
		return true;
	}

	private boolean isValid(Object value){
		//1)primary, primary array...
		return (value instanceof Timestamp || value instanceof BaseVo || BeanUtil.isSimpleProperty(value.getClass()));
	}

	@Override
	public int size() {
		return _underline.size();
	}

	@Override
	public boolean isEmpty() {
		return _underline.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return _underline.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _underline.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return _underline.get(key);
	}

	@Override
	public V remove(Object key) {
		return _underline.remove(key);
	}

	@Override
	public void clear() {
		_underline.clear();
	}

	@Override
	public Set<K> keySet() {
		return _underline.keySet();
	}

	@Override
	public Collection<V> values() {
		return _underline.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return _underline.entrySet();
	}
}
