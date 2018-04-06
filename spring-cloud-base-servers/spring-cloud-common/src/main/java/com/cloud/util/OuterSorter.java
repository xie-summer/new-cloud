package com.cloud.util;

import java.util.*;

/**
 * 用来定义外部字段的排序
 * @author gebiao(ge.biao@gewara.com)
 * @since Dec 6, 2012 2:24:51 PM
 * @param <T>
 */
public class OuterSorter<T> {
	private Map<Comparable, List<T>> beanMap = new TreeMap<Comparable, List<T>>();
	private List<T> nullList = new LinkedList<T>();
	private int count;
	private boolean nullAsc;
	public OuterSorter(boolean nullAsc){
		this.nullAsc = nullAsc;
	}
	public void addBean(Comparable sortValue, T bean){
		if(sortValue==null) {
			nullList.add(bean);
		}else{
			List<T> tmp = beanMap.get(sortValue);
			if(tmp==null){
				tmp = new ArrayList<T>(2);
				beanMap.put(sortValue, tmp);
			}
			tmp.add(bean);
		}
		count ++;
	}
	public List<T> getAscResult(){
		List<T> result = new ArrayList<T>(count);
		if(nullAsc){
			result.addAll(nullList);
		}
		for(Comparable key: beanMap.keySet()){
			result.addAll(beanMap.get(key));
		}
		if(!nullAsc){
			result.addAll(nullList);
		}
		return result;
	}
	public List<T> getDescResult(){
		List<T> result = new LinkedList<T>();
		for(Comparable key: beanMap.keySet()){
			result.addAll(0, beanMap.get(key));
		}
		if(nullAsc){
			result.addAll(nullList);
		}else{
			result.addAll(0, nullList);
		}
		return result;
	}
}
