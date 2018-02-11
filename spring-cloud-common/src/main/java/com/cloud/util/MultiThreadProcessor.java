package com.cloud.util;

import com.cloud.support.TExecutorThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author summer
 */
public class MultiThreadProcessor {
    private static final TLogger logger = LoggerUtils.getLogger(MultiThreadProcessor.class);
    private static final int EXECUTOR_KEEP_ALIVE_TIME = 60000;
    private static final int EXECUTOR_MAXIMUM_POOL_SIZE = Math.min(10, Runtime.getRuntime().availableProcessors());
    private static final int EXECUTOR_CORE_POOL_SIZE = 1;
    private static final int DEFAULT_EHCACHE_CLUSTERREDSTORE_MAX_CONCURRENCY = 4096;
    private ExecutorService executorService;

    public MultiThreadProcessor(int threadSize, String threadNamePre) {
        threadSize = Math.max(threadSize, 32);
        //所有的线程，必须通过ThreadPoolExecutor 创建，不能通过Executors创建
        executorService = new ThreadPoolExecutor(EXECUTOR_CORE_POOL_SIZE, threadSize, EXECUTOR_KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new TExecutorThreadFactory(threadNamePre));
//		executorService = Executors.newFixedThreadPool(threadSize, new TExecutorThreadFactory(threadNamePre));
    }

    /**
     * 内部多线程执行任务，同步返回
     *
     * @param taskList
     * @param maxwait  最大等待时间
     * @param unit
     * @return 成功次数，剩余次数
     */
    public long[] execute(Runnable[] taskList, Long maxwait, TimeUnit unit) {
        CountDownLatch cdl = new CountDownLatch(taskList.length);
        AtomicInteger counter = new AtomicInteger(0);
        for (Runnable task : taskList) {
            executorService.execute(new Worker(task, cdl, counter));
        }
        try {
            cdl.await(maxwait, unit);
        } catch (Exception e) {
        }
        return new long[]{counter.get(), cdl.getCount()};
    }

    private static class Worker implements Runnable {
        private Runnable task;
        private CountDownLatch cdl;
        private AtomicInteger counter;

        public Worker(Runnable task, CountDownLatch cdl, AtomicInteger counter) {
            this.task = task;
            this.cdl = cdl;
            this.counter = counter;
        }

        @Override
        public void run() {
            try {
                task.run();
                counter.incrementAndGet();
            } catch (Throwable e) {
                logger.warn(e, 50);
            } finally {
                cdl.countDown();
            }
        }
    }
}
