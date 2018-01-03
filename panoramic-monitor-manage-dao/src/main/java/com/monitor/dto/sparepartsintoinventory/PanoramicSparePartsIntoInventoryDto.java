package com.monitor.dto.sparepartsintoinventory;

import java.io.Serializable;
import com.cloud.model.BaseObject;

/**
 * 备件出入库表
 * @author xuegang
 *
 */
public class PanoramicSparePartsIntoInventoryDto extends BaseObject{
    
	private static final long serialVersionUID = 1L;
   
	/**
	 * 入出库产品名称
	 */
	private String name;
	
	/**
	 * 入出库产品统计值
	 */
	private double summary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public double getSummary() {
		return summary;
	}

	public void setSummary(double summary) {
		this.summary = summary;
	}

	@Override
    public Serializable realId() {
        return null;
    }
}
