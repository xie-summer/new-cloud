package com.weimob.mengdian.soa.kafka.consumer.service;

import com.google.common.collect.Maps;
import com.weimob.mengdian.soa.kafka.KafkaContextHolder;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.consumer.ConsumeWork;
import com.weimob.mengdian.soa.kafka.consumer.Consumer;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import com.weimob.mengdian.soa.kafka.model.KafkaConsumerMessage;
import com.weimob.mengdian.soa.kafka.model.KafkaPerThreadConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.KafkaTotalConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaCommitTypeEnum;
import com.weimob.mengdian.soa.kafka.utils.KafkaUtils;
import com.weimob.mengdian.soa.kafka.utils.StopWatch;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 单线程消费处理 支持手动提交offset
 *
 * @Author chenwp
 * @Date 2017-07-06 17:21
 * @Company WeiMob
 * @Description
 */
public class SingleThreadManualCommitService extends AbstractConsumeService {
    private KafkaConsumerConfig kafkaConsumerConfig;
    private List<ConsumerRecord<String, String>> executeRecords;
    private List<ConsumerRecord<String, String>> remainRecords;
    private int stepNo;
    private ConsumeWork consumeWork;
    private KafkaCommitTypeEnum kafkaCommitTypeEnum;
    private Integer pollingTotalCount;

    @Override
    public void init(List<ConsumerRecord<String, String>> consumerRecords, KafkaConsumerConfig kafkaConsumerConfig) {
        if (CollectionUtils.isEmpty(consumerRecords))
            return;

        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.kafkaCommitTypeEnum = kafkaConsumerConfig.getCommitType();
        /*初始化上下文*/
        KafkaContextHolder.init(KafkaUtils.buildKafkaConsumerMessageList(consumerRecords), kafkaConsumerConfig.getManualCommitFailStrategy(), kafkaConsumerConfig.getSessionTimeoutMs());

        this.stepNo = kafkaConsumerConfig.getConsumerStep();
        this.consumeWork = new ConsumeWork();
        this.remainRecords = consumerRecords;
        this.pollingTotalCount = consumerRecords.size();
        if (this.pollingTotalCount <= stepNo) {
            executeRecords = consumerRecords;
        } else {
            executeRecords = consumerRecords.subList(0, stepNo);
        }
    }


    @Override
    public void doConsume(long pollingTime, KafkaConsumer<String, String> consumerClient, Consumer consumerImpl) {
        if (CollectionUtils.isEmpty(executeRecords)
                || CollectionUtils.isEmpty(remainRecords))
            return;

        StopWatch totalConsumeStopWatch = new StopWatch();
        long longestConsumeTime = 0L;
        int totalConsumeCount = 0;
        //如果消息都消费完或者已经过了提交时间阀值或者用户设置等待则跳出循环
        while (!remainRecords.isEmpty()
                && System.currentTimeMillis() - pollingTime < KafkaContextHolder.getCommitIntervalThreshold()
                && KafkaContextHolder.getSleepTimeMs() == 0) {
            KafkaPerThreadConsumeInfo kafkaPerThreadConsumeInfo = consumeWork.work(executeRecords, consumerImpl, kafkaConsumerConfig);
            //消费总数
            totalConsumeCount += kafkaPerThreadConsumeInfo.getConsumeCount();
            if (longestConsumeTime < kafkaPerThreadConsumeInfo.getConsumeTime()) {
                //单个步长最大耗时
                longestConsumeTime = kafkaPerThreadConsumeInfo.getConsumeTime();
            }
            Map<String, KafkaConsumerMessage> partitionNoCommitOffsetMap = KafkaContextHolder.getPartitionNoCommitOffsetMap();
            if (MapUtils.isNotEmpty(partitionNoCommitOffsetMap)) {
                Iterator<ConsumerRecord<String, String>> iterator = remainRecords.iterator();
                while (iterator.hasNext()) {
                    ConsumerRecord<String, String> next = iterator.next();
                    int partition = next.partition();
                    String topic = next.topic();
                    long offset = next.offset();
                    String key = KafkaConstant.buildTopicPartitionOffsetKey(topic, partition, offset);
                    KafkaConsumerMessage kafkaConsumerMessage = partitionNoCommitOffsetMap.get(key);
                    //如果已经提交则移除
                    if (kafkaConsumerMessage == null) {
                        iterator.remove();
                    }
                }
                int size = remainRecords.size();
                if (size <= stepNo) {
                    executeRecords = remainRecords;
                } else {
                    executeRecords = remainRecords.subList(0, stepNo);
                }

            } else {
                break;
            }
        }
        //消费总耗时
        long totalConsumeTime = totalConsumeStopWatch.elapsedTime();
        KafkaTotalConsumeInfo kafkaTotalConsumeInfo = KafkaTotalConsumeInfo.of(this.kafkaConsumerConfig, this.pollingTotalCount, longestConsumeTime, totalConsumeCount, totalConsumeTime);
        //提交offset
        commitOffset(consumerClient, kafkaTotalConsumeInfo);
        //回滚到未提交的offset
        seekOffset(consumerClient);

        int sleepTime = KafkaContextHolder.getSleepTimeMs();
        try {
            Thread.sleep(sleepTime);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        KafkaContextHolder.clear();
    }

    private void commitOffset(KafkaConsumer<String, String> consumerClient, KafkaTotalConsumeInfo kafkaTotalConsumeInfo) {
        Map<TopicPartition, KafkaConsumerMessage> partitionCommitOffsetMap = KafkaContextHolder.getPartitionCommitOffsetMap();
        if (MapUtils.isEmpty(partitionCommitOffsetMap)) {
            return;
        }

        Map<TopicPartition, OffsetAndMetadata> offsets = Maps.newHashMap();

        for (Map.Entry<TopicPartition, KafkaConsumerMessage> kafkaConsumerMessageEntry : partitionCommitOffsetMap.entrySet()) {
            TopicPartition key = kafkaConsumerMessageEntry.getKey();
            KafkaConsumerMessage value = kafkaConsumerMessageEntry.getValue();

            OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(value.getOffset() + 1);
            offsets.put(key, offsetAndMetadata);
        }
        CommonService.commitOffset(consumerClient, offsets, kafkaCommitTypeEnum, kafkaTotalConsumeInfo);
    }

    private void seekOffset(KafkaConsumer<String, String> consumerClient) {
        Map<TopicPartition, Long> partitionSeekOffsetMap = KafkaContextHolder.getPartitionSeekOffsetMap();
        if (MapUtils.isEmpty(partitionSeekOffsetMap)) {
            return;
        }

        for (Map.Entry<TopicPartition, Long> topicPartitionLongEntry : partitionSeekOffsetMap.entrySet()) {
            TopicPartition key = topicPartitionLongEntry.getKey();
            Long offset = topicPartitionLongEntry.getValue();
            consumerClient.seek(key, offset);
        }
    }
}
