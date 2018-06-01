package com.cloud.schedule.task.job;

/**
 * @author summer
 * 2018/6/1
 */

import com.cloud.schedule.model.ScheduleTrigger;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

public class SimpleDataflowJob implements DataflowJob<ScheduleTrigger> {


    @Override
    public List<ScheduleTrigger> fetchData(ShardingContext shardingContext) {
    return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<ScheduleTrigger> list) {

    }
}