package com.cloud.service.task.manage.summary;

import com.cloud.service.api.manage.summary.RealTimeConsumptionSummaryTask;
import com.cloud.service.feign.manage.ManageFeignHystrixClient;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消耗数据定时任务汇总
 *
 * @author summer
 */
@Component
public class RealTimeConsumptionSummaryTaskImpl implements RealTimeConsumptionSummaryTask {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(RealTimeConsumptionSummaryTaskImpl.class);
    @Autowired
    private ManageFeignHystrixClient manageFeignHystrixClient;

    @Override
    public void execute(JobExecutionContext context) {

        try {
            DB_LOGGER.warn("<--消耗数据定时任务汇总  开始-->");
            manageFeignHystrixClient.realTimeConsumptionSummaryTask();
            DB_LOGGER.warn("<--消耗数据定时任务汇总 结束-->");
        } catch (Exception e) {
            DB_LOGGER.error("消耗数据定时任务汇总出现异常{}" + e);
        }
    }
}
