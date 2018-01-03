package com.monitor.api.dailyinventorysummary;

import com.cloud.core.Service;
import com.monitor.model.dailyinventorysummary.PanoramicDailyInventorySummary;

import java.util.List;
import java.util.Map;


/**
 * 库存服务
 *
 * @author summer
 * 2017/11/21.
 */
public interface PanoramicDailyInventorySummaryService extends Service<PanoramicDailyInventorySummary> {

    /**
     * 根据日期和物料编码查询库存（每种物料每天只有一条库存记录）
     *
     * @param code
     * @param date
     * @return
     */
    PanoramicDailyInventorySummary queryByDateAndCode(String code, String date);

    /**
     * 根据日期查询当前时间所有库存
     *
     * @param date
     * @return
     */
    List<PanoramicDailyInventorySummary> listByDateAndCode(String date);

    /**
     * 根据code校验数据高低状态
     *
     * @param codeList
     * @param date
     * @return
     */
    Map<String, String> check(List<String> codeList, String date);

    /**
     * 库存预计可使用天数
     *
     * @param code
     * @param date
     * @return
     */
    Integer countUsable(String code, String date);

    /**
     * 每日库存统计--定时任务
     *
     * @param date
     * @param records
     */
    void dailyInventorySummaryTask(String date, List<PanoramicDailyInventorySummary> records);

}
