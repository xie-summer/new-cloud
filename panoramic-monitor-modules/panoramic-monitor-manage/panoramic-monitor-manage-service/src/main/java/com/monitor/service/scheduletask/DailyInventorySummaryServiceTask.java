package com.monitor.service.scheduletask;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.api.dailyinventorysummary.PanoramicDailyInventorySummaryService;
import com.monitor.model.dailyinventorysummary.PanoramicDailyInventorySummary;

/**
 * @author summer
 */
//@Component
public class DailyInventorySummaryServiceTask implements Job {
	private static final transient TLogger DB_LOGGER = LoggerUtils
			.getLogger(AbnormalInformationRegularlyRefreshTask.class);
	@Autowired
	@Qualifier("dailyInventorySummaryService")
	private PanoramicDailyInventorySummaryService dailyInventorySummaryService;

	/**
	 * @param context
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			DB_LOGGER.warn("<----------每日库存定时刷新开始---------->");
			String date = DateUtil.getCurFullTimestampStr();
			List<PanoramicDailyInventorySummary> records = dailyInventorySummaryService
					.listByDateAndCode(DateUtil.getYestoryDate());
			dailyInventorySummaryService.dailyInventorySummaryTask(date, records);
			DB_LOGGER.warn("<----------每日库存定时刷新结束---------->");
		} catch (Exception e) {
			DB_LOGGER.warn("每日库存定时刷新" + e);
		}
	}
}
