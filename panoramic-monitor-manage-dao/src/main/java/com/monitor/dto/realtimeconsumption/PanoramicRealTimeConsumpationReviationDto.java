package com.monitor.dto.realtimeconsumption;

/**
 * 实时消耗偏差
 * @author gang
 *
 */
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

	public double getRealtimeConsumption() {
		return realtimeConsumption;
	}

	public void setRealtimeConsumption(double realtimeConsumption) {
		this.realtimeConsumption = realtimeConsumption;
	}

	public double getProductmaterialStock() {
		return productmaterialStock;
	}

	public void setProductmaterialStock(double productmaterialStock) {
		this.productmaterialStock = productmaterialStock;
	}

	public double getConsumptionReviation() {
		return consumptionReviation;
	}

	public void setConsumptionReviation(double consumptionReviation) {
		this.consumptionReviation = consumptionReviation;
	}
}
