package com.monitor.api.productofflinemeasurement;

import java.util.List;

import com.cloud.core.Service;
import com.monitor.dto.productmaterials.PanoramicProductMaterialsDto;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;
import com.monitor.model.realtimeconsumption.PanoramicRealTimeConsumption;


/**
* @author xuegang
* 2017/12/29.
*/
public interface PanoramicProductOfflineMeasurementService extends Service<PanoramicProductOfflineMeasurement> {
	
    /**
     * 磷钙实时汇总定时任务
     * @param date
     * @return
     */
	List<PanoramicProductMaterialsDto> findCalciumphosphateByDate(String date);
    
    /**
     * 普钙实时汇总定时任务
     * @param date
     * @return
     */
	List<PanoramicProductMaterialsDto> findCalciumsuperphosphateByDate(String date);

}
