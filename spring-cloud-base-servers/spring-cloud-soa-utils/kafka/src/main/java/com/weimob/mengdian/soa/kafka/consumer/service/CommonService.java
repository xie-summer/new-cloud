package com.weimob.mengdian.soa.kafka.consumer.service;

import com.weimob.mengdian.soa.kafka.model.KafkaTotalConsumeInfo;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaCommitTypeEnum;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaCommitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

/**
 * @Author chenwp
 * @Date 2017-07-08 15:37
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class CommonService {
    public static void commitOffset(KafkaConsumer<String, String> consumerClient, KafkaCommitTypeEnum kafkaCommitTypeEnum, KafkaTotalConsumeInfo kafkaTotalConsumeInfo) throws Exception {
        switch (kafkaCommitTypeEnum) {
            case SYNC:
                commitSync(consumerClient, kafkaTotalConsumeInfo);
                break;
            case ASYNC:
                commitAsync(consumerClient, kafkaTotalConsumeInfo);
                break;
            default:
                break;
        }
    }

    private static void commitSync(KafkaConsumer<String, String> consumerClient, KafkaTotalConsumeInfo kafkaTotalConsumeInfo) throws Exception {
        int stepNo = kafkaTotalConsumeInfo.getKafkaConsumerConfig().getConsumerStep();
        int commitThreshold = 4 * kafkaTotalConsumeInfo.getKafkaConsumerConfig().getHeartbeatIntervalMs() / 5;
        try {
            consumerClient.commitSync();
        } catch (CommitFailedException e) {
            throw new KafkaCommitException("kafka commit sync error!Poller提交总量:" + kafkaTotalConsumeInfo.getPollingTotalCount() +
                    ";提交超时:" + commitThreshold +
                    ";步长:" + stepNo +
                    ";已消费数量:" + kafkaTotalConsumeInfo.getTotalConsumeCount() +
                    ";已消费总耗时:" + kafkaTotalConsumeInfo.getTotalConsumeTime() + "毫秒" +
                    ";单步长最大消费耗时:" + kafkaTotalConsumeInfo.getLongestConsumeTime() + "毫秒"
                    , e);
        }
    }

    private static void commitAsync(KafkaConsumer<String, String> consumerClient, KafkaTotalConsumeInfo kafkaTotalConsumeInfo) {
        int stepNo = kafkaTotalConsumeInfo.getKafkaConsumerConfig().getConsumerStep();
        int commitThreshold = 4 * kafkaTotalConsumeInfo.getKafkaConsumerConfig().getHeartbeatIntervalMs() / 5;
        try {
            consumerClient.commitAsync();
        } catch (CommitFailedException e) {
            throw new KafkaCommitException("kafka commit sync error!Poller提交总量:" + kafkaTotalConsumeInfo.getPollingTotalCount() +
                    ";提交超时:" + commitThreshold +
                    ";步长:" + stepNo +
                    ";已消费数量:" + kafkaTotalConsumeInfo.getTotalConsumeCount() +
                    ";已消费总耗时:" + kafkaTotalConsumeInfo.getTotalConsumeTime() + "毫秒" +
                    ";单步长最大消费耗时:" + kafkaTotalConsumeInfo.getLongestConsumeTime() + "毫秒"
                    , e);
        }
    }

    public static void commitOffset(KafkaConsumer<String, String> consumerClient, Map<TopicPartition, OffsetAndMetadata> offsets, KafkaCommitTypeEnum kafkaCommitTypeEnum, KafkaTotalConsumeInfo kafkaTotalConsumeInfo) {
        switch (kafkaCommitTypeEnum) {
            case SYNC:
                commitSync(consumerClient, offsets, kafkaTotalConsumeInfo);
                break;
            case ASYNC:
                commitAsync(consumerClient, offsets, kafkaTotalConsumeInfo);
                break;
            default:
                break;
        }
    }

    private static void commitSync(KafkaConsumer<String, String> consumerClient, Map<TopicPartition, OffsetAndMetadata> offsets, KafkaTotalConsumeInfo kafkaTotalConsumeInfo) {
        int stepNo = kafkaTotalConsumeInfo.getKafkaConsumerConfig().getConsumerStep();
        int commitThreshold = 4 * kafkaTotalConsumeInfo.getKafkaConsumerConfig().getHeartbeatIntervalMs() / 5;
        try {
            consumerClient.commitSync(offsets);
        } catch (CommitFailedException e) {
            throw new KafkaCommitException("kafka commit sync error!Poller提交总量:" + kafkaTotalConsumeInfo.getPollingTotalCount() +
                    ";提交超时:" + commitThreshold +
                    ";步长:" + stepNo +
                    ";已消费数量:" + kafkaTotalConsumeInfo.getTotalConsumeCount() +
                    ";已消费总耗时:" + kafkaTotalConsumeInfo.getTotalConsumeTime() + "毫秒" +
                    ";单步长最大消费耗时:" + kafkaTotalConsumeInfo.getLongestConsumeTime() + "毫秒"
                    , e);
        }
    }

    private static void commitAsync(KafkaConsumer<String, String> consumerClient, Map<TopicPartition, OffsetAndMetadata> offsets, KafkaTotalConsumeInfo kafkaTotalConsumeInfo) {
        int stepNo = kafkaTotalConsumeInfo.getKafkaConsumerConfig().getConsumerStep();
        int commitThreshold = 4 * kafkaTotalConsumeInfo.getKafkaConsumerConfig().getHeartbeatIntervalMs() / 5;
        try {
            consumerClient.commitAsync(offsets, null);
        } catch (CommitFailedException e) {
            throw new KafkaCommitException("kafka commit sync error!Poller提交总量:" + kafkaTotalConsumeInfo.getPollingTotalCount() +
                    ";提交超时:" + commitThreshold +
                    ";步长:" + stepNo +
                    ";已消费数量:" + kafkaTotalConsumeInfo.getTotalConsumeCount() +
                    ";已消费总耗时:" + kafkaTotalConsumeInfo.getTotalConsumeTime() + "毫秒" +
                    ";单步长最大消费耗时:" + kafkaTotalConsumeInfo.getLongestConsumeTime() + "毫秒"
                    , e);
        }
    }
}
