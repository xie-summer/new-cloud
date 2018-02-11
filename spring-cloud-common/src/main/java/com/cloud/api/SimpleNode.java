package com.cloud.api;

import com.cloud.util.BeanUtil;
/**
 * @author summer
 */
public class SimpleNode extends ApiNode{
	private Object simpleValue;
	public SimpleNode(String nodeName, Object simpleValue, boolean ignoreEmpty){
		if(simpleValue!=null && BeanUtil.isSimpleValueType(simpleValue.getClass())){
			throw new IllegalArgumentException(simpleValue + " must be simple!");
		}
		this.nodeName = nodeName;
		this.simpleValue = simpleValue;
		this.ignoreEmpty = ignoreEmpty;
	}
	public Object getSimpleValue() {
		return simpleValue;
	}
}
