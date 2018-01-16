package com.monitor.dto.realtimeconsumption;

import lombok.Data;

/**
 * 实时消耗偏差
 * @author gang
 *
 */
@Data
public class PanoramicRealTimeConsumpationReviationDto {

	/**
	 * 消耗计量
	 */
	private double realtimeConsumption;
	
	/**
	 * 出入库数量
	 */
	private double productmaterialStock;
	
	/**
	 * 偏差对比
	 */
	private double consumptionReviation;
}
