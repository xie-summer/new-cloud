package com.weimob.mengdian.soa.kafka;

import com.weimob.mengdian.soa.kafka.model.KafkaConsumerMessage;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaManualCommitFailStrategyEnum;
import lombok.Data;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

/**
 * @Author chenwp
 * @Date 2017-07-04 19:28
 * @Company WeiMob
 * @Description
 */

@Data
public class KafkaContext {
    //分区提交的offset
    private Map<TopicPartition, KafkaConsumerMessage> partitionCommitOffsetMap;

    //分区回滚的offset
    private Map<TopicPartition, Long> partitionSeekOffsetMap;

    //分区未提交的信息
    private Map<String, KafkaConsumerMessage> partitionNoCommitOffsetMap;

    //分区最大的offset
    private Map<TopicPartition, Long> partitionMaxOffsetMap;

    //kafka提交异常采用的策略
    private KafkaManualCommitFailStrategyEnum kafkaManualCommitFailStrategyEnum;

    //提交间隔阀值
    private Integer commitIntervalThreshold;

    //本地执行完睡眠时间
    private ThreadLocal<Integer> sleepTimeMs = new ThreadLocal<>();

}
