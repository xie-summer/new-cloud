package com.monitor.dto.realtimeconsumption;

import com.cloud.model.BaseObject;
import java.io.Serializable;

/**
 * @author summer
 */
public class PanoramicRealTimeConsumptionDto extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 时间段
     */
    private int hour;

	/**
     * 汇总值
     */
    private Double value;

    /**
     * 获取汇总值
     *
     * @return value - 汇总值
     */
    public Double getValue() {
        return value;
    }

    /**
     * 设置汇总值
     *
     * @param value 汇总值
     */
    public void setValue(Double value) {
        this.value = value;
    }
    
    /**
     * 时间段
     * @return
     */
    public int getHour() {
		return hour;
	}

    /**
     * 设置时间段
     * @param hour
     */
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	@Override
	public Serializable realId() {
		// TODO Auto-generated method stub
		return null;
	}
   
}