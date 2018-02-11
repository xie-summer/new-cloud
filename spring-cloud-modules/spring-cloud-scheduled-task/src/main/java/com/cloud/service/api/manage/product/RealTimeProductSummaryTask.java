package com.cloud.service.api.manage.product;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author summer
 */
public interface RealTimeProductSummaryTask extends Job {
    /**
     * 执行定时任务
     *
     * @param context
     */
    @Override
    void execute(JobExecutionContext context);
}
