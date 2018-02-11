package com.cloud.service.task.manage.product;

import com.cloud.service.api.manage.product.RealTimeProductSummaryTask;
import com.cloud.service.feign.manage.ManageFeignHystrixClient;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 产品定时任务汇总
 *
 * @author summer
 */
@Component
public class RealTimeProductSummaryTaskImpl implements RealTimeProductSummaryTask {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(RealTimeProductSummaryTaskImpl.class);
    @Autowired
    private ManageFeignHystrixClient manageFeignHystrixClient;

    @Override
    public void execute(JobExecutionContext context) {
        try {
        	DB_LOGGER.warn("<--产品定时任务汇总  开始-->");
            manageFeignHystrixClient.realTimeProductSummaryTask();
            DB_LOGGER.warn("<--产品定时任务汇总  结束-->");
        } catch (Exception e) {
            DB_LOGGER.error("产品定时任务汇总出现异常{}" + e);
        }
    }
}
