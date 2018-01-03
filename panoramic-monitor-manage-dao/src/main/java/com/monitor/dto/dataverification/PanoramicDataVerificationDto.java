package com.monitor.dto.dataverification;

import java.io.Serializable;

/**
 * 
 * @author gang
 *
 */
public class PanoramicDataVerificationDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 采集计量
	 */
	private double valueAuto;
	
	/**
	 * 出入库计量
	 */
	private double valueManual;
	
	/**
	 * 偏差
	 */
	private double bias;
	
	public double getValueAuto() {
		return valueAuto;
	}
	
	public void setValueAuto(double valueAuto) {
		this.valueAuto = valueAuto;
	}
	
	public double getValueManual() {
		return valueManual;
	}
	
	public void setValueManual(double valueManual) {
		this.valueManual = valueManual;
	}
	
	public double getBias() {
		return bias;
	}
	
	public void setBias(double bias) {
		this.bias = bias;
	}
	
	
}
