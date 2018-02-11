package com.cloud.service.api.manage.stock;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author summer
 */
public interface DailyInventorySummaryTask extends Job {
    /**
     * 执行定时任务
     *
     * @param context
     */
    @Override
    void execute(JobExecutionContext context);
}
