package com.cloud.service.task.manage.stock;

import com.cloud.service.api.manage.stock.DailyInventorySummaryTask;
import com.cloud.service.feign.manage.ManageFeignHystrixClient;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author summer
 */
@Component
public class DailyInventorySummaryTaskImpl implements DailyInventorySummaryTask {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(DailyInventorySummaryTaskImpl.class);
    @Autowired
    private ManageFeignHystrixClient manageFeignHystrixClient;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            DB_LOGGER.warn("<--每日库存汇总定时任务  开始-->");
            manageFeignHystrixClient.dailyInventorySummaryTask();
            DB_LOGGER.warn("<--每日库存汇总定时任务  结束-->");
        } catch (Exception e) {
            DB_LOGGER.error("每日库存汇总定时任务出现异常{}" + e);
        }
    }
}
