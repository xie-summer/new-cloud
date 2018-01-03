package com.cloud.service.task.test;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.cloud.service.api.test.TestService;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;

/**
 * @author summer
 */
@Service
public class TestServiceImpl implements TestService {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(TestServiceImpl.class);

    @Override
    public void test() {

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

//        try {
//            productMaterialFeignHystrixClient.realTimeProductSummaryTask();
//        } catch (Exception e) {
//            DB_LOGGER.warn("产品定时任务汇总出现异常{}" + e);
//        }
    }
}
