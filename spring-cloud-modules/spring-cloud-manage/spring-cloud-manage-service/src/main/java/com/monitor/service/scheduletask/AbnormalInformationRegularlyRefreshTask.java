package com.monitor.service.scheduletask;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.api.intothefactoryrecords.PanoramicIntoTheFactoryRecordsService;
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
public class AbnormalInformationRegularlyRefreshTask implements Job {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(AbnormalInformationRegularlyRefreshTask.class);
    @Autowired
    @Qualifier("intoTheFactoryRecordsService")
    private PanoramicIntoTheFactoryRecordsService intoTheFactoryRecordsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            DB_LOGGER.warn("<----------异常出库信息状态定时刷新开始---------->");
            intoTheFactoryRecordsService.regularlyRefreshTask();
            DB_LOGGER.warn("<----------异常出库信息状态定时刷新结束---------->");
        } catch (Exception e) {
            DB_LOGGER.warn("异常出库信息状态定时刷新出错{}"+e);
        }
    }
}
