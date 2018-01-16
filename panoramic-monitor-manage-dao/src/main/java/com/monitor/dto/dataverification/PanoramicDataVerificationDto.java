package com.monitor.dto.dataverification;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author gang
 *
 */
@Data
public class PanoramicDataVerificationDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 采集计量
	 */
	private String valueAuto;
	
	/**
	 * 出入库计量
	 */
	private String valueManual;
	
	/**
	 * 偏差
	 */
	private String bias;

	public String getValueAuto() {
		return valueAuto;
	}

	public void setValueAuto(String valueAuto) {
		this.valueAuto = valueAuto;
	}

	public String getValueManual() {
		return valueManual;
	}

	public void setValueManual(String valueManual) {
		this.valueManual = valueManual;
	}

	public String getBias() {
		return bias;
	}

	public void setBias(String bias) {
		this.bias = bias;
	}
}
