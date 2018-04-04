package com.monitor.api.productionmonitoring;

import com.cloud.core.IService;
import com.monitor.dto.productionmonitoring.Productionmonitoringinfo;
import com.monitor.model.productionmonitoring.PanoramicProductionMonitoring;


/**
* @author summer
* 2017/12/20.
*/
public interface PanoramicProductionMonitoringService extends IService<PanoramicProductionMonitoring> {
	
    /**
     * 指定时间取得生产监控内容
     * @param date
     * @return
     */
	Productionmonitoringinfo findByDate(String date);
    
}
