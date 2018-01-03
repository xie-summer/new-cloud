package com.monitor.service.scheduletask;

import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.api.inventoryentry.PanoramicInventoryEntryService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author summer
 */
//@Component
public class ManualEntryExceptionRecordTask implements Job {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(ManualEntryExceptionRecordTask.class);
    @Autowired
    @Qualifier("inventoryEntryService")
    private PanoramicInventoryEntryService inventoryEntryService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            DB_LOGGER.warn("<----------人工录入异常信息状态定时刷新开始---------->");
            String date = DateUtil.getYestoryDate();
            inventoryEntryService.manualEntryExceptionRecordTask(date);
            DB_LOGGER.warn("<----------人工录入异常信息状态定时刷新结束---------->");
        } catch (Exception e) {
            DB_LOGGER.warn("人工录入异常信息信息状态定时刷新出错{}" + e);
        }
    }
}
