package com.cloud.service.api.test;

import org.quartz.Job;

/**
 * @author summer
 * 测试案例
 */
public interface TestService extends Job {

    /**
     * 测试案例
     */
    public void test();
}
