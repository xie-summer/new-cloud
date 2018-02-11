package com.cloud.task;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author summer
 */
@Component
public class BaseJob implements Job {

    public BaseJob() {
    }

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        boolean isExecute = false;  //是否已执行业务逻辑
        boolean flag = false;  //业务逻辑执行后返回结果
        try {
            //可以通过context拿到执行当前任务的quartz中的很多信息，如当前是哪个trigger在执行该任务
            CronTrigger trigger = (CronTrigger) context.getTrigger();
            String corn = trigger.getCronExpression();
            String jobName = trigger.getKey().getName();
            String jobGroup = trigger.getKey().getGroup();
        } catch (Exception e) {
        }
    }

}