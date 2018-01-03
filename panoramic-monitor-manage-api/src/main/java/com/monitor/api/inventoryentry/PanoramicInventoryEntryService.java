package com.monitor.api.inventoryentry;

import com.cloud.core.Service;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;
import com.monitor.model.inventoryentry.PanoramicInventoryEntry;

import java.util.List;


/**
 * @author summer
 * 2017/11/30
 */
public interface PanoramicInventoryEntryService extends Service<PanoramicInventoryEntry> {

    /**
     * 上传数据
     *
     * @param panoramicInventoryEntryList
     * @return
     */
    void saveOrUpdate(List<PanoramicInventoryEntry> panoramicInventoryEntryList);

    /**
     * 扫描人工录入数据 - 定时任务
     *
     * @param date
     */
    void manualEntryExceptionRecordTask(String date);

    /**
     * 根据时间查询当天数据
     *
     * @param date
     * @return
     */
    List<PanoramicInventoryEntry> findByDate(String date);

    /**
     * 查询未录入数据信息
     *
     * @param date
     * @return
     */
    List<PanoramicExceptionRecord> findMsgByDate(String date);
}
