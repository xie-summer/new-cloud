package com.monitor.mapper.realtimeconsumptiongather;

import com.cloud.core.Mapper;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;
import java.util.List;
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
            "and code = #{code} and date_format(gather_time,'%y%m%d%H') = date_format( #{date} ,'%y%m%d%H') limit 1")
    PanoramicRealTimeConsumptionGather selectByGatherTime(@Param("code") String code, @Param("date") String date);
    
    /**
     * 查询N天数据
     *
     * @param code
     * @param number
     * @param date
     * @return
     */
    @Select("SELECT\n" + 
    		"	sum(value) as value \n" + 
    		"FROM\n" + 
    		"	panoramic_real_time_consumption_gather\n" + 
    		"WHERE\n" + 
    		"	CODE = #{code} AND\n" + 
    		"	delete_flag =  1 AND\n" + 
    		"	f_id = 2 AND\n" + 
    		"	date(gather_time) < #{date}\n" + 
    		"GROUP BY\n" + 
    		"	TO_DAYS(gather_time)\n" +
    		"HAVING \n" + 
    		"	value > 1\n" +     		
    		"ORDER BY\n" + 
    		"	gather_time DESC\n" + 
    		"limit #{number};\n" + 
    		"")
    List<PanoramicRealTimeConsumptionGather> findNumberdayData(@Param("code") String code,@Param("number") Integer number, @Param("date") String date);

}