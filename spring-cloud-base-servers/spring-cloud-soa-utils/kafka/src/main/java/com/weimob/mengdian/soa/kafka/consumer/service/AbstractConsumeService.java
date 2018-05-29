package com.weimob.mengdian.soa.kafka.consumer.service;

import com.weimob.mengdian.soa.kafka.consumer.Consumer;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-07-06 17:18
 * @Company WeiMob
 * @Description
 */
public abstract class AbstractConsumeService {

    public abstract void init(List<ConsumerRecord<String, String>> consumerRecords, KafkaConsumerConfig kafkaConsumerConfig);

    public abstract void doConsume(long pollingTime, KafkaConsumer<String, String> consumerClient, Consumer consumerImpl) throws Exception;

    public void consume(List<ConsumerRecord<String, String>> consumerRecords, KafkaConsumerConfig kafkaConsumerConfig, long pollingTime, KafkaConsumer<String, String> consumerClient, Consumer consumerImpl) throws Exception {
        if (CollectionUtils.isEmpty(consumerRecords)) {
            return;
        }
        init(consumerRecords, kafkaConsumerConfig);
        doConsume(pollingTime, consumerClient, consumerImpl);
    }
}
