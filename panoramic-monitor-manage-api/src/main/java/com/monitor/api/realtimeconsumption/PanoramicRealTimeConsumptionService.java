package com.monitor.api.realtimeconsumption;

import com.cloud.core.Service;
import com.monitor.dto.realtimeconsumption.PanoramicRealTimeConsumptionDto;
import com.monitor.model.realtimeconsumption.PanoramicRealTimeConsumption;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * @author summer
 * 2017/11/21.
 */
public interface PanoramicRealTimeConsumptionService extends Service<PanoramicRealTimeConsumption> {

    /**
     * 汇总指定code和date的消耗数据到汇总表中(定时任务)
     *
     * @param name
     * @param code
     * @param date
     */
    void realtimeConsumptionSummaryTask(String name ,String code, String date);

    /** 查询所有消耗分类(定时任务)
     * @return
     */
    List<PanoramicRealTimeConsumption> listRealTimeConsumptionCategoryTask();
    
    /**
     * 消耗数据定时任务汇总
     * @return
     */
	void realTimeConsumptionSummaryTask();
    
}
