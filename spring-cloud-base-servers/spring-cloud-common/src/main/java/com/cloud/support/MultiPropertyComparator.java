package com.cloud.support;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.Comparator;
/**
 * @author summer
 */
public class MultiPropertyComparator<T> implements Comparator<T> {
	private String[] properties = null;
	private boolean[] asc;
	private int length = 0;
	
	public MultiPropertyComparator(String[] properties, boolean[] asc){
		this.properties = properties;
		length = Math.min(properties.length, asc.length);
		this.asc = asc;
	}
	@Override
	public int compare(T o1, T o2) {
		int result = 0;
		if(o1!=null && o2==null){
			result = 1;
		}else if(o1==null && o2!=null){
			result = -1;
		}else if(o1!=null && o2!=null){
			for(int i=0; i< length; i++){
				try{
					Comparable p1 =  (Comparable) PropertyUtils.getProperty(o1, properties[i]);
					Comparable p2 =  (Comparable) PropertyUtils.getProperty(o2, properties[i]);
					if(p1==null && p2!=null) {
                        result = -1;
                    } else if(p2==null && p1!=null) {
                        result = 1;
                    } else if(p1!=null && p2!=null) {
                        result = p1.compareTo(p2);
                    }
					if(result!=0) {
                        return asc[i] ? result : -result;
                    }
				}catch(Exception e){
					//ignore
				}
			}
		}
		return 0;
	}
}
