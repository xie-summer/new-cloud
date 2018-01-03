package com.monitor.service.scheduletask;

import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.api.realtimeconsumption.PanoramicRealTimeConsumptionService;
import com.monitor.model.realtimeconsumption.PanoramicRealTimeConsumption;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实时消耗数据汇总到汇总表
 *
 * @author summer 定时任务必须实现Job接口
 */
//@Component
public class RealtimeConsumptionSummaryTask implements Job {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(RealtimeConsumptionSummaryTask.class);
    @Autowired
    @Qualifier("realTimeConsumptionService")
    private PanoramicRealTimeConsumptionService realTimeConsumptionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String date = DateUtil.currentTimeHourStr();
            List<PanoramicRealTimeConsumption> consumptionCategoryList = realTimeConsumptionService.listRealTimeConsumptionCategoryTask();
            if (null == consumptionCategoryList || consumptionCategoryList.size() == 0) {
                DB_LOGGER.warn("实时消耗表数据为空{}");
                return;
            }
            consumptionCategoryList.forEach((PanoramicRealTimeConsumption e) -> {
                realTimeConsumptionService.realtimeConsumptionSummaryTask(e.getName(), e.getCode(), date);
            });
        } catch (Exception e) {
            DB_LOGGER.warn("实时消耗数据汇总到汇总表{},出现异常"+e);
        }
    }

}
