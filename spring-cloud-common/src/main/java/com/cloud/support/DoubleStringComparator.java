package com.cloud.support;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.Comparator;
/**
 * @author summer
 */
public class DoubleStringComparator<T> implements Comparator<T>{
	private String property = null;
	private boolean asc;
	/**
	 * @param property
	 * @param asc
	 */
	public DoubleStringComparator(String property, boolean asc){
		this.property = property;
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
			try{
				String p1 = "" + PropertyUtils.getProperty(o1, property);
				String p2 = "" + PropertyUtils.getProperty(o2, property);
				Double n1 = null, n2 = null;
				try{n1=Double.valueOf(p1);}catch(Exception e){}
				try{n2=Double.valueOf(p2);}catch(Exception e){}
				if(n1==null && n2!=null) {
                    result = -1;
                } else if(n2==null && n1!=null) {
                    result = 1;
                } else if(n1!=null && n2!=null) {
                    result = n1.compareTo(n2);
                }
				if(result!=0) {
                    return asc ? result : -result;
                }
			}catch(Exception e){
			}
		}
		return 0;
	}
}
