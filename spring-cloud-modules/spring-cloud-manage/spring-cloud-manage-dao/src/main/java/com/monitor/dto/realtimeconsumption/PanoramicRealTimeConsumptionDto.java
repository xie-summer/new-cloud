package com.monitor.dto.realtimeconsumption;

import com.cloud.model.BaseObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author summer
 */
@Data
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
   
}