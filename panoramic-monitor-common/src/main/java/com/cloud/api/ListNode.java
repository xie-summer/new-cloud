package com.cloud.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author summer
 */
public class ListNode extends ApiNode {
	private List beanList;
	private List<Map<String, ?>> dataMapList = new ArrayList<Map<String, ?>>();
	private String singleNodeName;
	public List getBeanList() {
		return beanList;
	}
	public List<Map<String, ?>> getDataMapList() {
		return dataMapList;
	}
	public String getSingleNodeName() {
		return singleNodeName;
	}
	public ListNode(List beanList, String nodeName, String singleNodeName, boolean ignoreEmpty, String... outputFields){
		this.beanList = beanList;
		this.nodeName = nodeName;
		this.ignoreEmpty = ignoreEmpty;
		this.singleNodeName = singleNodeName;
		if(outputFields!=null && beanList!=null){
			for(Object bean: beanList){
				dataMapList.add(getBeanMap(bean, outputFields));
			}
		}
	}
}
