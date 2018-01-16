package com.monitor.dto.productionmonitoring;

import lombok.Data;

/**
 * @author gang
 */
@Data
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
    /**
     * 磷钙电耗
     */
    private double pcc;
}
