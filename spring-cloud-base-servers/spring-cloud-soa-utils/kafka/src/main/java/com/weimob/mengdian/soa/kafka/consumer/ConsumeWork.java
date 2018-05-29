package com.weimob.mengdian.soa.kafka.consumer;

import com.weimob.mengdian.soa.cat.CatTransaction;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import com.weimob.mengdian.soa.kafka.model.KafkaPerThreadConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.KafkaPolledMessages;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaFailStrategyEnum;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaCommitException;
import com.weimob.mengdian.soa.kafka.utils.KafkaUtils;
import com.weimob.mengdian.soa.kafka.utils.StopWatch;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-06-24 23:01
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class ConsumeWork {
    private Consumer consumerImpl;
    private KafkaPolledMessages kafkaPolledMessages;
    /**
     * 失败的重试次数
     */
    private Integer retryCount;

    /**
     * 重试间隔 单位ms
     */
    private Integer retryInterval;
    private KafkaFailStrategyEnum failStrategy;

    /**
     * 执行onFail方法失败之后的重试间隔 单位ms
     */
    private Integer callOnFailExceptionRetryIntervalMs;
    private String topic;

    @SuppressWarnings("unchecked")
    private void init(List<ConsumerRecord<String, String>> consumerRecords, Consumer consumerImpl, KafkaConsumerConfig kafkaConsumerConfig) {
        this.consumerImpl = consumerImpl;

        this.retryCount = kafkaConsumerConfig.getRetryCount();
        this.retryInterval = kafkaConsumerConfig.getRetryIntervalMs();
        this.failStrategy = kafkaConsumerConfig.getFailStrategy();
        this.topic = kafkaConsumerConfig.getTopic();
        this.callOnFailExceptionRetryIntervalMs = kafkaConsumerConfig.getCallOnFailExceptionRetryIntervalMs();

         /*获取类实现的Consumer接口的泛型类型*/
        Class<?> interfaceGenericType = KafkaUtils.getInterfaceGenericType(consumerImpl.getClass(), Consumer.class.getName());
        this.kafkaPolledMessages = KafkaUtils.buildKafkaConsumerMessageList(consumerRecords, interfaceGenericType);
    }


    public KafkaPerThreadConsumeInfo work(List<ConsumerRecord<String, String>> consumerRecords, Consumer consumerImpl, KafkaConsumerConfig kafkaConsumerConfig) {
        if (CollectionUtils.isEmpty(consumerRecords)) {
            return null;
        }
        StopWatch consumeTime = new StopWatch();
        int consumeCount = 0;
        init(consumerRecords, consumerImpl, kafkaConsumerConfig);
        int chance = retryCount + 1;


        boolean isExceptionCaught = false;
        while (chance > 0) {
            isExceptionCaught = doConsume();
            chance--;
            if (isExceptionCaught && chance > 0) {
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            } else {
                break;
            }
        }

        //重试范围内未成功
        if (isExceptionCaught) {
            val catKafkaConsumerOnFail = new CatTransaction(KafkaConstant.KAFKA_CALL_ON_FAIL, topic);
            try {
                this.consumerImpl.onFail();
                catKafkaConsumerOnFail.success();
            } catch (Exception e) {
                catKafkaConsumerOnFail.error(e);
                // if execute onFail failed do
                callOnFailException();
            }
        } else {
            consumeCount = consumerRecords.size();
        }
        consumeTime.elapsedTime();
        return KafkaPerThreadConsumeInfo.of(consumeTime.elapsedTime(), consumeCount);
    }


    /**
     * 消费方法
     */
    @SuppressWarnings("unchecked")
    private boolean doConsume() {
        val catKafkaConsumer = new CatTransaction(KafkaConstant.KAFKA_CONSUMER, topic);
        try {
            this.consumerImpl.consume(kafkaPolledMessages);
            catKafkaConsumer.success();
            return false;
        } catch (KafkaCommitException e) {
            catKafkaConsumer.error(e);
            throw e;
        } catch (Exception e) {
            catKafkaConsumer.error(e);
            return true;
        }
    }

    /**
     * 重试消费方法直到成功
     */
    private void retryStrategy() {
        while (true) {
            boolean isExceptionCaught = doConsume();
            if (isExceptionCaught) {
                try {
                    Thread.sleep(callOnFailExceptionRetryIntervalMs);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            } else {
                break;
            }
        }
    }

    /**
     * 当执行onFail方法失败之后执行
     */
    private void callOnFailException() {
        switch (this.failStrategy) {
            case RETRY:
                // 一直重试 直到成功
                retryStrategy();
                break;
            case DISCARD:
                break;
            default:
                break;
        }
    }
}
