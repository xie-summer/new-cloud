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

	@Override
	public Serializable realId() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
   
}