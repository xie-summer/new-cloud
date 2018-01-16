package com.monitor.service.scheduletask;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.api.productmaterials.PanoramicProductMaterialsService;
import com.monitor.model.productmaterials.PanoramicProductMaterials;

/**
 * 产品实时汇总定时任务
 *
 * @author summer
 */
//@Component
public class RealtimeProductSummaryTask implements Job {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(RealtimeProductSummaryTask.class);
    @Autowired
    @Qualifier("productMaterialsService")
    private PanoramicProductMaterialsService productMaterialsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String date = DateUtil.currentTimeHourStr();
            List<PanoramicProductMaterials> categoryList = productMaterialsService.listRealTimeProductSummaryCategoryTask();
            if (null == categoryList || categoryList.size() == 0) {
                DB_LOGGER.warn("实时消耗表数据为空{}");
                return;
            }
            categoryList.forEach(e -> {
                productMaterialsService.productSummaryTask(e.getName(), e.getCode(), date);
            });
        } catch (Exception e) {
            DB_LOGGER.warn("实时消耗数据汇总到汇总表{},出现异常"+e);
        }
    }

}
