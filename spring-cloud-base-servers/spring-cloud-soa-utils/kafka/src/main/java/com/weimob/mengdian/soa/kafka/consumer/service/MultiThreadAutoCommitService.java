package com.weimob.mengdian.soa.kafka.consumer.service;

import com.google.common.collect.Lists;
import com.weimob.mengdian.soa.kafka.consumer.Consumer;
import com.weimob.mengdian.soa.kafka.consumer.KafkaConsumeWorker;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import com.weimob.mengdian.soa.kafka.model.KafkaPerThreadConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.KafkaTotalConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaCommitTypeEnum;
import com.weimob.mengdian.soa.kafka.utils.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author chenwp
 * @Date 2017-07-07 10:51
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class MultiThreadAutoCommitService extends AbstractConsumeService {
    private KafkaConsumerConfig kafkaConsumerConfig;
    private ExecutorService consumerExecutor;
    private List<List<ConsumerRecord<String, String>>> threadRecords;
    private KafkaCommitTypeEnum kafkaCommitTypeEnum;
    //总的消息数
    private int pollingTotalCount;
    /**
     * 接收线程返回
     */
    private List<Future<KafkaPerThreadConsumeInfo>> futureList = Lists.newArrayList();




    @Override
    public void init(List<ConsumerRecord<String, String>> consumerRecords, KafkaConsumerConfig kafkaConsumerConfig) {
        if (CollectionUtils.isEmpty(consumerRecords)) {
            return;
        }
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        consumerExecutor = Executors.newFixedThreadPool(kafkaConsumerConfig.getConsumerNumber());
        this.threadRecords = Lists.partition(consumerRecords,kafkaConsumerConfig.getConsumerStep());
        this.pollingTotalCount = consumerRecords.size();
        this.kafkaCommitTypeEnum = kafkaConsumerConfig.getCommitType();

    }


    @Override
    public void doConsume(long pollingTime, KafkaConsumer<String, String> consumerClient, Consumer consumerImpl) throws Exception{
        StopWatch totalConsumeStopWatch = new StopWatch();
        for (List<ConsumerRecord<String, String>> threadRecord : threadRecords) {
            futureList.add(consumerExecutor.submit(new KafkaConsumeWorker(threadRecord, consumerImpl, kafkaConsumerConfig)));
        }
        long longestConsumeTime = 0L;
        int totalConsumeCount = 0;
        for (Future<KafkaPerThreadConsumeInfo> future : futureList) {
            try {
                KafkaPerThreadConsumeInfo kafkaPerThreadConsumeInfo = future.get();
                if (kafkaPerThreadConsumeInfo == null) {
                    continue;
                }

                //消费总数
                totalConsumeCount += kafkaPerThreadConsumeInfo.getConsumeCount();
                if (longestConsumeTime < kafkaPerThreadConsumeInfo.getConsumeTime()) {
                    //单个步长最大耗时
                    longestConsumeTime = kafkaPerThreadConsumeInfo.getConsumeTime();
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("kafka consumer thread execute interrupted or failed", e);
            }
        }

        //消费总耗时
        long totalConsumeTime = totalConsumeStopWatch.elapsedTime();
        KafkaTotalConsumeInfo kafkaTotalConsumeInfo = KafkaTotalConsumeInfo.of(this.kafkaConsumerConfig, this.pollingTotalCount, longestConsumeTime, totalConsumeCount, totalConsumeTime);
        CommonService.commitOffset(consumerClient, kafkaCommitTypeEnum, kafkaTotalConsumeInfo);
    }
}
