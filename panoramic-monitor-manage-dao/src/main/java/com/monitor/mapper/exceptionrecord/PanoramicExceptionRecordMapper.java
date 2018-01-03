package com.monitor.mapper.exceptionrecord;

import com.cloud.core.Mapper;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author summer
 */
@Repository("exceptionRecordMapper")
public interface PanoramicExceptionRecordMapper extends Mapper<PanoramicExceptionRecord> {

    /** 查询
     * @param category
     * @param date
     * @param number
     * @return
     */
    @Select("select * from panoramic_exception_record where delete_flag=1 and alarm_item = #{category} and date_sub(#{date}, INTERVAL #{number} DAY) <= date(alarm_time)")
    List<PanoramicExceptionRecord> findMsgByDate(@Param("category") String category, @Param("date") String date, @Param("number") Integer number);
}