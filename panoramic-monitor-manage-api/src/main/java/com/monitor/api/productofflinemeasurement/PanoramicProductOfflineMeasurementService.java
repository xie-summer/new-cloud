package com.monitor.api.productofflinemeasurement;


import com.cloud.core.Service;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;


/**
* @author xuegang
* 2017/12/29.
*/
public interface PanoramicProductOfflineMeasurementService extends Service<PanoramicProductOfflineMeasurement> {
		    
    /**
     * 产品下线数据定时任务汇总
     * @return
     */
	void productOfflineMeasurementSummaryTask();

}
