package com.monitor.mapper.dailyinventorysummary;

import com.cloud.core.Mapper;
import com.monitor.model.dailyinventorysummary.PanoramicDailyInventorySummary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author summer
 */
@Repository("dailyInventorySummaryMapper")
public interface PanoramicDailyInventorySummaryMapper extends Mapper<PanoramicDailyInventorySummary> {
    /**
     * 查询7天数据
     *
     * @param code
     * @param number
     * @param date
     * @return
     */
    @Select("select * from panoramic_daily_inventory_summary where code = #{code} and date_sub(#{date}, INTERVAL #{number} DAY) <= date(utime)")
    List<PanoramicDailyInventorySummary> findNumberdayData(@Param("code") String code,@Param("number") Integer number, @Param("date") String date);

    /**批量更新
     * @param curRecords
     */
    void updateBatch(List<PanoramicDailyInventorySummary> curRecords);
}