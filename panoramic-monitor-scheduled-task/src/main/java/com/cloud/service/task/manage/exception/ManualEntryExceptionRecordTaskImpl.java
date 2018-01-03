package com.cloud.service.task.manage.exception;

import com.cloud.service.api.manage.exception.ManualEntryExceptionRecordTask;
import com.cloud.service.feign.manage.ManageFeignHystrixClient;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 异常信息状态定时刷新
 *
 * @author summer
 */
@Component
public class ManualEntryExceptionRecordTaskImpl implements ManualEntryExceptionRecordTask {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(ManualEntryExceptionRecordTaskImpl.class);
    @Autowired
    private ManageFeignHystrixClient manageFeignHystrixClient;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            DB_LOGGER.warn("<--异常信息状态定时刷新  开始-->");
            manageFeignHystrixClient.manualEntryExceptionRecordTask();
            DB_LOGGER.warn("<--异常信息状态定时刷新  结束-->");
        } catch (Exception e) {
            DB_LOGGER.error("异常信息状态定时刷新出现异常{}" + e);
        }
    }
}
