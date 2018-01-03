package com.monitor.service.scheduletask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.api.exceptionrecord.PanoramicExceptionRecordService;

/**
 * 
 * @author summer 定时任务必须实现Job接口
 *
 */
//@Component
public class Test implements Job {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(Test.class);
	@Autowired
	@Qualifier("exceptionRecordService")
	private PanoramicExceptionRecordService exceptionRecordService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DB_LOGGER.warn(DateUtil.currentTimeStr());
		DB_LOGGER.warn("哈哈，执行了定时任务");

	}

}
