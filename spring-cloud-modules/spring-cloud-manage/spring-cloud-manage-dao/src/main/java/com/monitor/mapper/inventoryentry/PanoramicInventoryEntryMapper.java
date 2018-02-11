package com.monitor.mapper.inventoryentry;

import com.cloud.core.Mapper;
import com.monitor.model.inventoryentry.PanoramicInventoryEntry;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author summer
 */
@Repository("inventoryEntryMapper")
public interface PanoramicInventoryEntryMapper extends Mapper<PanoramicInventoryEntry> {

    /**
     * 根据code,date,classno查询录入记录
     *
     * @param code
     * @param date
     * @param schedule
     * @return
     */
    @Select(" select * from panoramic_inventory_entry where  in_or_out =1  and delete_flag=1 "
            + " and  code =#{code} and date_format(utime,'%Y%m%d') = date_format(#{date},'%Y%m%d') and schedule =#{schedule} limit 1")
    PanoramicInventoryEntry selectByCodeAndTime(@Param("code") String code, @Param("date") Date date,
                                                @Param("schedule") String schedule);

    /**
     * 根据时间获取前6条数据
     * @param date
     * @return
     */
    @Select(" select * from panoramic_inventory_entry where  in_or_out =1  and delete_flag=1 "
            + "  and date_format(utime,'%Y%m%d') = date_format(#{date},'%Y%m%d')  limit 6")
    List<PanoramicInventoryEntry> findByDate(String date);
}