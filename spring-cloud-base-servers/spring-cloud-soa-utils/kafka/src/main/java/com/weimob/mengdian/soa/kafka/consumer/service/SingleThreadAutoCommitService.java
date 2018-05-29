package com.weimob.mengdian.soa.kafka.consumer.service;

import com.google.common.collect.Lists;
import com.weimob.mengdian.soa.kafka.consumer.ConsumeWork;
import com.weimob.mengdian.soa.kafka.consumer.Consumer;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import com.weimob.mengdian.soa.kafka.model.KafkaPerThreadConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.KafkaTotalConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaCommitTypeEnum;
import com.weimob.mengdian.soa.kafka.utils.StopWatch;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-07-07 10:32
 * @Company WeiMob
 * @Description
 */
public class SingleThreadAutoCommitService extends AbstractConsumeService {
    private KafkaConsumerConfig kafkaConsumerConfig;
    private List<List<ConsumerRecord<String, String>>> threadRecords;

    private KafkaCommitTypeEnum kafkaCommitTypeEnum;
    //总的消息数
    private int pollingTotalCount;


    @Override
    public void init(List<ConsumerRecord<String, String>> consumerRecords, KafkaConsumerConfig kafkaConsumerConfig) {
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        int stepNo = kafkaConsumerConfig.getConsumerStep();
        this.threadRecords = Lists.partition(consumerRecords, stepNo);
        this.kafkaCommitTypeEnum = kafkaConsumerConfig.getCommitType();
        this.pollingTotalCount = consumerRecords.size();
    }


    @Override
    public void doConsume(long pollingTime, KafkaConsumer<String, String> consumerClient, Consumer consumerImpl) throws Exception {
        StopWatch totalConsumeStopWatch = new StopWatch();
        ConsumeWork consumeWork = new ConsumeWork();
        long longestConsumeTime = 0L;
        int totalConsumeCount = 0;
        for (List<ConsumerRecord<String, String>> threadRecord : threadRecords) {
            KafkaPerThreadConsumeInfo kafkaPerThreadConsumeInfo = consumeWork.work(threadRecord, consumerImpl, kafkaConsumerConfig);

            if (kafkaPerThreadConsumeInfo == null) {
                continue;
            }
            //消费总数
            totalConsumeCount += kafkaPerThreadConsumeInfo.getConsumeCount();
            if (longestConsumeTime < kafkaPerThreadConsumeInfo.getConsumeTime()) {
                //单个步长最大耗时
                longestConsumeTime = kafkaPerThreadConsumeInfo.getConsumeTime();
            }

        }
        //消费总耗时
        long totalConsumeTime = totalConsumeStopWatch.elapsedTime();
        KafkaTotalConsumeInfo kafkaTotalConsumeInfo = KafkaTotalConsumeInfo.of(this.kafkaConsumerConfig, this.pollingTotalCount, longestConsumeTime, totalConsumeCount, totalConsumeTime);
        CommonService.commitOffset(consumerClient, kafkaCommitTypeEnum, kafkaTotalConsumeInfo);
    }
}
