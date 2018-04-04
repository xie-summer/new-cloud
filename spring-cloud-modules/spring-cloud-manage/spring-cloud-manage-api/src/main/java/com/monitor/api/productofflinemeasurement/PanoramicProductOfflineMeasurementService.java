package com.monitor.api.productofflinemeasurement;


import com.cloud.core.IService;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;


/**
* @author summer
* 2017/12/29.
*/
public interface PanoramicProductOfflineMeasurementService extends IService<PanoramicProductOfflineMeasurement> {
		    
    /**
     * 产品下线数据定时任务汇总
     * @return
     */
	void productOfflineMeasurementSummaryTask();

}
