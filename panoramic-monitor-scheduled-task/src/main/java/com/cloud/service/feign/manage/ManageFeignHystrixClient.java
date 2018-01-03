package com.cloud.service.feign.manage;

import com.cloud.configure.FeignConfiguration;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author summer
 */
@FeignClient(name = "monitor-zuul", configuration = FeignConfiguration.class, fallback = ManageFeignHystrixClient.HystrixClientFallback.class)
@Service
public interface ManageFeignHystrixClient {

    /**
     * 产品实时汇总
     */
    @RequestLine("POST /manage/product/materials/task")
    void realTimeProductSummaryTask();

    /**
     * 实时消耗数据汇总
     */
    @RequestLine("POST /manage/real/time/consumption/task")
    void realTimeConsumptionSummaryTask();

    /**
     * 异常信息状态定时刷新
     */
    @RequestLine("POST /manage/panoramic/inventory/entry/task")
    void manualEntryExceptionRecordTask();

    /**
     * 每日库存定时汇总
     */
    @RequestLine("POST /manage/daily/inventory/summary/task")
    void dailyInventorySummaryTask();

    /**
     * 异常出库信息状态定时刷新
     */
    @RequestLine("POST /manage/into/the/factory/records/task")
    void regularlyRefreshTask();

    /**
     * @author summer
     */
    @Component
    class HystrixClientFallback implements ManageFeignHystrixClient {

        private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(HystrixClientFallback.class);

        /**
         * hystrix fallback方法
         */
        @Override
        public void realTimeProductSummaryTask() {
            DB_LOGGER.warn("产品实时汇总-调度manage服务发生异常，进入fallback方法{},产品实时汇总失败");
            return;
        }

        @Override
        public void realTimeConsumptionSummaryTask() {
            DB_LOGGER.warn("消耗数据定时任务汇总-调度manage服务发生异常，进入fallback方法{},消耗数据定时任务汇总");
            return;
        }

        @Override
        public void manualEntryExceptionRecordTask() {
            DB_LOGGER.warn("异常信息状态定时刷新-调度manage服务发生异常，进入fallback方法{},异常信息状态定时刷新失败");
            return;

        }

        @Override
        public void dailyInventorySummaryTask() {
            DB_LOGGER.warn("每日库存汇总定时任务-调度manage服务发生异常，进入fallback方法{},每日库存汇总定时任务失败");
            return;
        }

        @Override
        public void regularlyRefreshTask() {
            DB_LOGGER.warn("异常出库信息状态定时刷新-调度manage服务发生异常，进入fallback方法{},异常出库信息状态定时刷新失败");
            return;
        }

    }
}
