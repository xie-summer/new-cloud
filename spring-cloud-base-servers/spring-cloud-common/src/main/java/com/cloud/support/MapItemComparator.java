package com.cloud.support;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Comparator;
import java.util.Map;

/**
 * @author Administrator
 * 
 * 比较两个map中elname元素的property属性
 */
public class MapItemComparator implements Comparator<Map> {
	private String elname, property;
	boolean desc;
	public MapItemComparator(String elname, String property, boolean desc) {
		this.elname = elname;
		this.property = property;
		this.desc = desc;
	}
	@Override
	public int compare(Map one, Map another) {
		Comparable o1 = (Comparable) one.get(elname); 
		Comparable o2 = (Comparable) another.get(elname);
		int result = 0;
		if(o1 == null && o2 == null || o1==o2) {
            return 0;
        }
		if(o1 == null && o2 != null){
			result = -1;
		}else if(o1 != null && o2 == null){
			result = 1;
		}else{
			if(StringUtils.isBlank(property)){
				result = o1.compareTo(o2);
			}else{
				result = compareProperty(o1, o2, property);
			}
		}
		if(desc){
			return -result;
		}else{
			return result;
		}
	}
	private static int compareProperty(Object o1, Object o2, String prop){
		int result = 0;
		try{
			Comparable p1 =  (Comparable) PropertyUtils.getProperty(o1, prop);
			Comparable p2 =  (Comparable) PropertyUtils.getProperty(o2, prop);
			if(p1==null && p2!=null) {
                result = -1;
            } else if(p2==null && p1!=null) {
                result = 1;
            } else if(p1!=null && p2!=null) {
                result = p1.compareTo(p2);
            }
		}catch(Exception e){
		}
		return result;
	}
}
