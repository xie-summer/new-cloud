package com.monitor.mapper.realtimeconsumptiongather;

import com.cloud.core.Mapper;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("realTimeConsumptionGatherMapper")
public interface PanoramicRealTimeConsumptionGatherMapper extends Mapper<PanoramicRealTimeConsumptionGather> {

    /**
     * 根据汇总时间和code查询汇总记录
     *
     * @param code
     * @param date
     * @return
     */
    @Select(" select * from panoramic_real_time_consumption_gather  where delete_flag =1 " +
            "and code = #{code} and date_format(gather_time,'%y%m%d%h') = date_format( #{date} ,'%y%m%d%h') limit 1")
    PanoramicRealTimeConsumptionGather selectByGatherTime(@Param("code") String code, @Param("date") String date);
}