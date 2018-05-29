package com.weimob.mengdian.soa.kafka.consumer;

import com.weimob.mengdian.soa.kafka.Compatible;
import com.weimob.mengdian.soa.kafka.annotation.KafkaConsumed;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaDefaultConsumerConfig;
import com.weimob.mengdian.soa.kafka.utils.KafkaUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @Author chenwp
 * @Date 2017-06-21 20:19
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaConsumerManager {
    private Consumer consumerImpl;
    private Properties kafkaConsumerConfig;
    private KafkaDefaultConsumerConfig kafkaDefaultConsumerConfig;
    private final KafkaConsumed kafkaConsumed;

    public KafkaConsumerManager(Consumer consumerImpl, Properties kafkaConsumerConfig, KafkaDefaultConsumerConfig kafkaDefaultConsumerConfig) {
        this.consumerImpl = consumerImpl;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.kafkaConsumed = consumerImpl.getClass().getAnnotation(KafkaConsumed.class);
        this.kafkaDefaultConsumerConfig = kafkaDefaultConsumerConfig;
    }

    /**
     * 唤醒分组线程开始工作
     */
    public void wakeupGroupWorker() {
        //根据优先级获取kafka配置
        KafkaConsumerConfig mergeKafkaConsumerConfig = new KafkaConsumerConfig(kafkaConsumed, kafkaConsumerConfig, kafkaDefaultConsumerConfig);

        int groupNumber = mergeKafkaConsumerConfig.getGroupNumber ();

        //获取消费类指定泛型
        Class<?> interfaceGenericType = KafkaUtils.getInterfaceGenericType(consumerImpl.getClass(), Consumer.class.getName());

        //泛型实现了兼容模式接口则只能有一个分组
        if(interfaceGenericType != null && Compatible.class.isAssignableFrom(interfaceGenericType)){
            groupNumber = 1;
        }

        groupNumber = groupNumber > KafkaConstant.MAX_GROUP_NO ? KafkaConstant.MAX_GROUP_NO : groupNumber;

        for (int i = 0; i < groupNumber; i++) {
            Thread thread = new Thread(new KafkaGroupWorker(mergeKafkaConsumerConfig, consumerImpl, i));
            thread.start();
        }
    }
}
