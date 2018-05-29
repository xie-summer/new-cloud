package com.cloud.service.acl;

import com.cloud.mapper.ScheduleTriggerMapper;
import com.cloud.model.ScheduleTrigger;
import com.cloud.support.TExecutorThreadFactory;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author summer
 */
@Service
public class ScheduleTriggerService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTriggerService.class);
    /**
     * 状态 0 无效 1有效
     */
    private static final String STATUS = "0";
    /**
     * 获取锁的最大延迟时间（s）
     */
    private static final int DELAY = 10;
    @Autowired
    private Scheduler scheduler;

    @Autowired
    @Qualifier("scheduleTriggerMapper")
    private ScheduleTriggerMapper scheduleTriggerMapper;
    private static volatile ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new TExecutorThreadFactory("scheduleJob"));

    /**
     * 定时刷新扫描配置表
     */
    @Scheduled(fixedRate = 1000 * 30)
    public void refreshTrigger() {
        ReentrantLock lock = new ReentrantLock();

        try {
            if (lock.tryLock(DELAY, TimeUnit.SECONDS)) {
                List<ScheduleTrigger> jobList = selectEffective();
                if (null != jobList && jobList.size() > 0) {
                    for (ScheduleTrigger scheduleJob : jobList) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                CronTrigger trigger = getTrigger(scheduleJob);
                                if (!Optional.ofNullable(trigger).isPresent()) {
                                    scheduleJob(scheduleJob);
                                } else {
                                    TriggerKey triggerKey = getTriggerKey(scheduleJob);
                                    rescheduleJob(trigger, triggerKey, scheduleJob);
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            logger.error("定时任务每日刷新触发器任务异常，在ScheduleTriggerService的方法refreshTrigger中，异常信息：", e);
        } finally {
            lock.unlock();
        }
    }

    private List<ScheduleTrigger> selectEffective() {
        Condition condition = new Condition(ScheduleTrigger.class, false);
        condition.createCriteria().andCondition(" status='1'  ");
        condition.setOrderByClause(" job_group desc ");
        return scheduleTriggerMapper.selectByCondition(condition);
    }

    private void rescheduleJob(CronTrigger trigger, TriggerKey triggerKey, ScheduleTrigger scheduleJob) {
        String searchCron = scheduleJob.getCron();
        String currentCron = trigger.getCronExpression();
        if (!searchCron.equals(currentCron)) {
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
                    .build();
            // 按新的trigger重新设置job执行
            try {
                scheduler.rescheduleJob(triggerKey, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteJob(ScheduleTrigger scheduleJob) {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("执行任务调度出现异常SchedulerException {}", e);
        }
    }

    private void scheduleJob(ScheduleTrigger scheduleJob) {
        try {
            CronTrigger trigger;
            JobDetail jobDetail = JobBuilder
                    .newJob((Class<? extends Job>) Class.forName(scheduleJob.getJobName()))
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(scheduleJob.getCron());
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            logger.error("执行任务调度出现异常ClassNotFoundException {}", e);
        } catch (SchedulerException e) {
            logger.error("SchedulerException {}", e);
        }
    }

    private CronTrigger getTrigger(ScheduleTrigger scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob);
            return (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (Exception e) {
            logger.error("获取调度触发器出现异常 {}", e);
        }
        return null;
    }

    private TriggerKey getTriggerKey(ScheduleTrigger scheduleJob) {
        return TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }
}