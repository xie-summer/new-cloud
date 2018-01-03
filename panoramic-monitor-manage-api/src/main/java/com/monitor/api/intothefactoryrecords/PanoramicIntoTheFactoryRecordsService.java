package com.monitor.api.intothefactoryrecords;

import com.cloud.core.Service;
import com.monitor.model.intothefactoryrecords.PanoramicIntoTheFactoryRecords;

import java.util.List;


/**
 * @author summer
 * 2017/11/29
 */

public interface PanoramicIntoTheFactoryRecordsService extends Service<PanoramicIntoTheFactoryRecords> {

    /**
     * 根据时间查询进出厂信息
     *
     * @param date
     * @return
     */
    List<PanoramicIntoTheFactoryRecords> listByDate(String date);

    /**
     * 查询最新一条记录
     *
     * @param date
     * @return
     */
    PanoramicIntoTheFactoryRecords findByDate(String date);

    /**
     * 统计当天异常出库记录
     *
     * @param date
     * @return
     */
    Integer count(String date);

    /**
     * 定时任务 -定时刷新异常出库异常信息状态
     */
    void regularlyRefreshTask();
}
