package com.cloud.api;

import java.util.Map;
/**
 * @author summer
 */
public class BeanNode extends ApiNode{
	private Object nodeBean;
	private Map dataMap;
	public Object getNodeBean() {
		return nodeBean;
	}
	public BeanNode(Object bean, String nodeName, boolean ignoreEmpty, String... outputFields){
		this.nodeBean = bean;
		this.nodeName = nodeName;
		this.ignoreEmpty = ignoreEmpty;
		if(outputFields!=null && nodeBean!=null){
			dataMap = getBeanMap(nodeBean, outputFields);
		}
	}
	public Map<String, ?> getDataMap() {
		return dataMap;
	}
}
