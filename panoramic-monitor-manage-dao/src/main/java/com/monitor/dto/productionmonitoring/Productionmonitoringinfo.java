package com.monitor.dto.productionmonitoring;

/**
 * 
 * @author gang
 *
 */
public class Productionmonitoringinfo {

	/**
	 * 磷钙矿耗
	 */
	private double cpoc;
	
	/**
	 * 磷钙酸耗
	 */
	private double cpac;
	
	/**
	 * 磷钙煤耗
	 */
	private double ccp;
	
	/**
	 * 普钙电耗
	 */
	private double cpc;

	public double getCpoc() {
		return cpoc;
	}

	public void setCpoc(double cpoc) {
		this.cpoc = cpoc;
	}

	public double getCpac() {
		return cpac;
	}

	public void setCpac(double cpac) {
		this.cpac = cpac;
	}

	public double getCcp() {
		return ccp;
	}

	public void setCcp(double ccp) {
		this.ccp = ccp;
	}

	public double getCpc() {
		return cpc;
	}

	public void setCpc(double cpc) {
		this.cpc = cpc;
	}
}
