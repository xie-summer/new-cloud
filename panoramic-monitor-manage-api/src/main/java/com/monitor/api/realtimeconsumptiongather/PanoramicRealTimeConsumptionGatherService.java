package com.monitor.api.realtimeconsumptiongather;

import com.cloud.core.Service;
import com.monitor.dto.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherDto;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * @author summer
 * 2017/11/21.
 */
public interface PanoramicRealTimeConsumptionGatherService extends Service<PanoramicRealTimeConsumptionGather> {

    /**
     * 根据时间和code查询实时消耗汇总数据
     *
     * @param date
     * @param code
     * @return
     */
    List<PanoramicRealTimeConsumptionGather> listByCodeAndDate(String date, String code);

    /**
     * 根据时间进行月度统计
     *
     * @param date
     * @param code
     * @return
     */
    PanoramicRealTimeConsumptionGather queryMonthlyStatisticsByDate(String date, String code);

    /**
     * 根据时间进行每日统计
     *
     * @param date
     * @param code
     * @return
     */
    PanoramicRealTimeConsumptionGatherDto queryDayStatisticsByDate(String date, String code);
    
    /**
     * 查询N天内消化总量
     * @param code
     * @param number
     * @param date
     * @return
     */
    List<PanoramicRealTimeConsumptionGather> findNumberdayData(String code,Integer number,String date);
}
