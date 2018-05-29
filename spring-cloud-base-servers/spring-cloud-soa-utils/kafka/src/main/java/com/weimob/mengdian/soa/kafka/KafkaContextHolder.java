package com.weimob.mengdian.soa.kafka;

import com.google.common.collect.Maps;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.model.KafkaConsumerMessage;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaManualCommitFailStrategyEnum;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaCommitException;
import com.weimob.mengdian.soa.utils.encrypt.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenwp
 * @Date 2017-07-04 16:48
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaContextHolder {
    /**
     * 当前线程是否提交offset
     */
    private static final ThreadLocal<KafkaContext> KAFKA_CONTEXT = new ThreadLocal<>();

    public static void init(List<KafkaConsumerMessage> noCommitKafkaConsumerMessages, KafkaManualCommitFailStrategyEnum kafkaManualCommitFailStrategyEnum, int sessionTimeoutMs) {
        KafkaContext kafkaContext = new KafkaContext();

        Map<TopicPartition, Long> partitionMaxOffsetMap = Maps.newHashMap();
        Map<TopicPartition, Long> partitionSeekOffsetMap = Maps.newHashMap();
        //初始化未提交的消息为全部消息，customizeCommitOffset方法进行维护
        Map<String, KafkaConsumerMessage> partitionNoCommitOffsetMap = Maps.newHashMap();
        for (KafkaConsumerMessage consumerMessage : noCommitKafkaConsumerMessages) {
            partitionNoCommitOffsetMap.put(KafkaConstant.buildTopicPartitionOffsetKey(consumerMessage.getTopic(), consumerMessage.getPartition(), consumerMessage.getOffset()), consumerMessage);
            TopicPartition topicPartition = new TopicPartition(consumerMessage.getTopic(), consumerMessage.getPartition());
            Long seekOffset = partitionSeekOffsetMap.get(topicPartition);
            Long maxOffset = partitionMaxOffsetMap.get(topicPartition);
            //初始化每个分区回滚的offset为分区最小的offset
            if (seekOffset == null || seekOffset > consumerMessage.getOffset()) {
                partitionSeekOffsetMap.put(topicPartition, consumerMessage.getOffset());
            }

            if (maxOffset == null || maxOffset < consumerMessage.getOffset()) {
                partitionMaxOffsetMap.put(topicPartition, consumerMessage.getOffset());
            }
        }

        kafkaContext.setPartitionNoCommitOffsetMap(partitionNoCommitOffsetMap);
        kafkaContext.setKafkaManualCommitFailStrategyEnum(kafkaManualCommitFailStrategyEnum);
        kafkaContext.setPartitionSeekOffsetMap(partitionSeekOffsetMap);
        kafkaContext.setPartitionMaxOffsetMap(partitionMaxOffsetMap);

        kafkaContext.setPartitionCommitOffsetMap(new HashMap<TopicPartition, KafkaConsumerMessage>());
        //控制提交间隔阀值为 heartbeat.interval.ms (默认sessionTimeoutMs的三分之一)的80%
        kafkaContext.setCommitIntervalThreshold(4 * sessionTimeoutMs / 15);
        KAFKA_CONTEXT.set(kafkaContext);
    }

    public static Map<TopicPartition, KafkaConsumerMessage> getPartitionCommitOffsetMap() {
        KafkaContext kafkaContext = KAFKA_CONTEXT.get();
        return kafkaContext.getPartitionCommitOffsetMap();
    }

    public static Map<String, KafkaConsumerMessage> getPartitionNoCommitOffsetMap() {
        KafkaContext kafkaContext = KAFKA_CONTEXT.get();
        return kafkaContext.getPartitionNoCommitOffsetMap();
    }

    public static Map<TopicPartition, Long> getPartitionSeekOffsetMap() {
        KafkaContext kafkaContext = KAFKA_CONTEXT.get();
        return kafkaContext.getPartitionSeekOffsetMap();
    }

    public static int getCommitIntervalThreshold() {
        KafkaContext kafkaContext = KAFKA_CONTEXT.get();
        return kafkaContext.getCommitIntervalThreshold();
    }

    /**
     * 拉取间隔时间 毫秒
     *
     * @return
     */
    public static int getSleepTimeMs() {
        KafkaContext kafkaContext = KAFKA_CONTEXT.get();
        if (kafkaContext != null) {
            ThreadLocal<Integer> sleepTimeMsThreadLocal = kafkaContext.getSleepTimeMs();
            return sleepTimeMsThreadLocal.get() == null ? 0 : sleepTimeMsThreadLocal.get();
        }
        return 0;
    }

    public static void setSleepTimeMs(Integer sleepTimeMs) {
        KafkaContext kafkaContext = KAFKA_CONTEXT.get();
        ThreadLocal<Integer> sleepTimeMsThreadLocal = kafkaContext.getSleepTimeMs();
        sleepTimeMsThreadLocal.set(sleepTimeMs);
    }

    public static void customizeCommitOffset(KafkaConsumerMessage kafkaConsumerMessage) {
        String topic = kafkaConsumerMessage.getTopic();
        Integer partition = kafkaConsumerMessage.getPartition();
        Long offset = kafkaConsumerMessage.getOffset();
        Object value = kafkaConsumerMessage.getValue();
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        KafkaContext kafkaContext = KAFKA_CONTEXT.get();
        Map<TopicPartition, KafkaConsumerMessage> partitionCommitOffsetMap = kafkaContext.getPartitionCommitOffsetMap();
        Map<String, KafkaConsumerMessage> partitionNoCommitOffsetMap = kafkaContext.getPartitionNoCommitOffsetMap();
        Map<TopicPartition, Long> partitionMaxOffsetMap = kafkaContext.getPartitionMaxOffsetMap();
        KafkaManualCommitFailStrategyEnum kafkaManualCommitFailStrategyEnum = kafkaContext.getKafkaManualCommitFailStrategyEnum();
        Map<TopicPartition, Long> partitionSeekOffsetMap = kafkaContext.getPartitionSeekOffsetMap();
        //之前已经提交的信息
        KafkaConsumerMessage priorCommitMessage = partitionCommitOffsetMap.get(topicPartition);

        if (priorCommitMessage != null && priorCommitMessage.getOffset() > offset) {/*如果提交的offset小于之前提交的offset*/
            String errMsg = "kafka manual commit error,you should commit in order."
                    + "topic :" + topic + " partition:" + partition + " offset:" + offset + " value:"
                    + "[" + JsonUtils.writeValueAsString(value) + "] should not commit after "
                    + "topic :" + priorCommitMessage.getTopic() + " partition:" + priorCommitMessage.getPartition() + " offset:" + priorCommitMessage.getOffset() + " value:"
                    + "[" + JsonUtils.writeValueAsString(priorCommitMessage.getValue()) + "]";
            log.error(errMsg);

            if (KafkaManualCommitFailStrategyEnum.THROW_EXCEPTION.equals(kafkaManualCommitFailStrategyEnum)) {
                throw new KafkaCommitException(errMsg);
            }
        } else {
            partitionCommitOffsetMap.put(topicPartition, kafkaConsumerMessage);
            partitionNoCommitOffsetMap.remove(KafkaConstant.buildTopicPartitionOffsetKey(topic, partition, offset));

            Long maxOffset = partitionMaxOffsetMap.get(topicPartition);
            Long seekOffset = offset + 1;
            //分区全部消费完，不需要回溯
            if (seekOffset > maxOffset) {
                partitionSeekOffsetMap.remove(topicPartition);
            } else {
                partitionSeekOffsetMap.put(topicPartition, seekOffset);
            }
        }
    }

    public static void clear() {
        KAFKA_CONTEXT.remove();
    }
}
