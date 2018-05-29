package com.weimob.mengdian.soa.kafka.consumer;

import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import com.weimob.mengdian.soa.kafka.model.KafkaPerThreadConsumeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author chenwp
 * @Date 2017-06-22 18:51
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaConsumeWorker implements Callable<KafkaPerThreadConsumeInfo> {
    private List<ConsumerRecord<String, String>> consumerRecords;
    private Consumer consumerImpl;
    private KafkaConsumerConfig kafkaConsumerConfig;


    public KafkaConsumeWorker(List<ConsumerRecord<String, String>> consumerRecords, Consumer consumerImpl, KafkaConsumerConfig kafkaConsumerConfig) {
        this.consumerRecords = consumerRecords;
        this.consumerImpl = consumerImpl;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }

    @Override
    public KafkaPerThreadConsumeInfo call() {
        return new ConsumeWork().work(consumerRecords, consumerImpl, kafkaConsumerConfig);
    }
}
