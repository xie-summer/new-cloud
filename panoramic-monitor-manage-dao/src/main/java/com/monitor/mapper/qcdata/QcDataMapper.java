package com.monitor.mapper.qcdata;

import com.cloud.core.Mapper;
import com.monitor.model.qcdata.QcData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author summer
 */
@Repository("qcDataMapper")
public interface QcDataMapper extends Mapper<QcData> {
    /**
     * 根据时间查询
     *
     * @param date
     * @param type
     * @return
     */
    @Select(" select * from qc_data where  date_format(date,'%Y%m%d') = date_format(#{date},'%Y%m%d') " +
            "and type=#{type}  order by date desc ,sys_date desc")
    List<QcData> listByDate(@Param("date") String date, @Param("type") String type);
}