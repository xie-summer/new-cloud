package com.weimob.mengdian.soa.kafka.utils;

import com.weimob.mengdian.soa.kafka.annotation.KafkaConsumed;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.consumer.config.KafkaDefaultConsumerConfig;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaCommitTypeEnum;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaConsumerConfigEnum;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaFailStrategyEnum;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaManualCommitFailStrategyEnum;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaConfigSettingException;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

import static com.weimob.mengdian.soa.kafka.model.enums.KafkaConsumerConfigEnum.*;

/**
 * @Author chenwp
 * @Date 2017-07-16 13:38
 * @Company WeiMob
 * @Description
 */
public class KafkaConfigUtils {
    public static Object getKafkaConsumerConfig(KafkaConsumerConfigEnum kafkaConsumerConfigEnum, KafkaConsumed annotation, Properties kafkaConsumerConfig, KafkaDefaultConsumerConfig kafkaDefaultConsumerConfig) {
        switch (kafkaConsumerConfigEnum) {
            case KAFKA_BOOTSTRAP_SERVERS:
                //先取注解值
                String bootstrapServers = annotation.bootstrapServers();
                if (StringUtils.isNotBlank(bootstrapServers)) {
                    return bootstrapServers;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_BOOTSTRAP_SERVERS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_BOOTSTRAP_SERVERS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getBootstrapServers();
            case KAFKA_CONSUMER_TOPIC:
                //先取注解值
                String topic = annotation.topic();
                if (StringUtils.isNotBlank(topic)) {
                    return topic;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CONSUMER_TOPIC.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_CONSUMER_TOPIC.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getTopic();
            case KAFKA_GROUP_NUMBER:
                //先取注解值
                int groupNumber = annotation.groupNumber();
                if (groupNumber != KafkaConstant.NUMBER_DEFAULT) {
                    return groupNumber;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_GROUP_NUMBER.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_GROUP_NUMBER.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getGroupNumber();
            case KAFKA_GROUP_ID:
                //先取注解值
                String groupId = annotation.groupId();
                if (StringUtils.isNotBlank(groupId)) {
                    return groupId;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_GROUP_ID.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_GROUP_ID.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getGroupId();
            case KAFKA_POLLER_NUMBER:
                //先取注解值
                int pollerNumber = annotation.pollerNumber();
                if (pollerNumber != KafkaConstant.NUMBER_DEFAULT) {
                    return pollerNumber;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_POLLER_NUMBER.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_POLLER_NUMBER.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getPollerNumber();
            case KAFKA_CONSUMER_NUMBER:
                //先取注解值
                int consumerNumber = annotation.consumerNumber();
                if (consumerNumber != KafkaConstant.NUMBER_DEFAULT) {
                    return consumerNumber;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CONSUMER_NUMBER.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_CONSUMER_NUMBER.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getConsumerNumber();
            case KAFKA_CONSUMER_STEP:
                //先取注解值
                int consumerStep = annotation.consumerStep();
                if (consumerStep != KafkaConstant.NUMBER_DEFAULT) {
                    return consumerStep;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CONSUMER_STEP.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_CONSUMER_STEP.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getConsumerStep();
            case KAFKA_MAX_POLL_RECORDS:
                //先取注解值
                int maxPollRecords = annotation.maxPollRecords();
                if (maxPollRecords != KafkaConstant.NUMBER_DEFAULT) {
                    return maxPollRecords;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_MAX_POLL_RECORDS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_MAX_POLL_RECORDS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getMaxPollRecords();
            case KAFKA_READTIME_MS:
                //先取注解值
                int readTimeMs = annotation.readTimeMs();
                if (readTimeMs != KafkaConstant.NUMBER_DEFAULT) {
                    return readTimeMs;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_READTIME_MS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_READTIME_MS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getReadTimeMs();
            case KAFKA_RETRY_COUNT:
                //先取注解值
                int retryCount = annotation.retryCount();
                if (retryCount != KafkaConstant.NUMBER_DEFAULT) {
                    return retryCount;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_RETRY_COUNT.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_RETRY_COUNT.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getRetryCount();
            case KAFKA_RETRYINTERVAL_MS:
                //先取注解值
                int retryIntervalMs = annotation.retryIntervalMs();
                if (retryIntervalMs != KafkaConstant.NUMBER_DEFAULT) {
                    return retryIntervalMs;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_RETRYINTERVAL_MS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_RETRYINTERVAL_MS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getRetryIntervalMs();
            case KAFKA_CALLONFAILEXCEPTION_RETRYINTERVAL_MS:
                //先取注解值
                int callOnFailExceptionRetryIntervalMs = annotation.callOnFailExceptionRetryIntervalMs();
                if (callOnFailExceptionRetryIntervalMs != KafkaConstant.NUMBER_DEFAULT) {
                    return callOnFailExceptionRetryIntervalMs;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CALLONFAILEXCEPTION_RETRYINTERVAL_MS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_CALLONFAILEXCEPTION_RETRYINTERVAL_MS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getCallOnFailExceptionRetryIntervalMs();
            case KAFKA_FAILSTRATEGY:
                //先取注解值
                KafkaFailStrategyEnum kafkaFailStrategyEnum = annotation.failStrategy();
                if (!KafkaFailStrategyEnum.NOP.equals(kafkaFailStrategyEnum)) {
                    return kafkaFailStrategyEnum;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_FAILSTRATEGY.getCode()) != null) {
                    String failStrategy = (String) kafkaConsumerConfig.get(KAFKA_FAILSTRATEGY.getCode());
                    return KafkaFailStrategyEnum.getByCode(failStrategy);
                }
                //最后取mcc默认配置
                return KafkaFailStrategyEnum.getByCode(kafkaDefaultConsumerConfig.getFailStrategy());
            case KAFKA_SESSION_TIMEOUT_MS:
                //先取注解值
                int sessionTimeoutMs = annotation.sessionTimeoutMs();
                if (sessionTimeoutMs != KafkaConstant.NUMBER_DEFAULT) {
                    return sessionTimeoutMs;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_SESSION_TIMEOUT_MS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_SESSION_TIMEOUT_MS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getSessionTimeoutMs();
            case KAFKA_HEARTBEAT_INTERVAL_MS:
                //先取注解值
                int heartbeatIntervalMs = annotation.heartbeatIntervalMs();
                if (heartbeatIntervalMs != KafkaConstant.NUMBER_DEFAULT) {
                    return heartbeatIntervalMs;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_HEARTBEAT_INTERVAL_MS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_HEARTBEAT_INTERVAL_MS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getHeartbeatIntervalMs();
            case KAFKA_REQUEST_TIMEOUT_MS:
                //先取注解值
                int requestTimeoutMs = annotation.requestTimeoutMs();
                if (requestTimeoutMs != KafkaConstant.NUMBER_DEFAULT) {
                    return requestTimeoutMs;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_REQUEST_TIMEOUT_MS.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_REQUEST_TIMEOUT_MS.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getRequestTimeoutMs();
            case KAFKA_KEY_DESERIALIZER:
                //先取注解值
                String keyDeserializer = annotation.keyDeserializer();
                if (StringUtils.isNotBlank(keyDeserializer)) {
                    return keyDeserializer;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_KEY_DESERIALIZER.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_KEY_DESERIALIZER.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getKeyDeserializer();
            case KAFKA_VALUE_DESERIALIZER:
                //先取注解值
                String valueDeserializer = annotation.valueDeserializer();
                if (StringUtils.isNotBlank(valueDeserializer)) {
                    return valueDeserializer;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_VALUE_DESERIALIZER.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_VALUE_DESERIALIZER.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getValueDeserializer();
            case KAFKA_IMMEDIATELYEXIT_WHENERROR:
                //先取注解值
                boolean immediatelyExitWhenError = annotation.immediatelyExitWhenError();
                if (immediatelyExitWhenError) {
                    return true;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_IMMEDIATELYEXIT_WHENERROR.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_IMMEDIATELYEXIT_WHENERROR.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getImmediatelyExitWhenError();
            case KAFKA_AUTO_COMMIT:
                //先取注解值
                boolean autoCommit = annotation.autoCommit();
                if (!autoCommit) {
                    return false;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_AUTO_COMMIT.getCode()) != null) {
                    return kafkaConsumerConfig.get(KAFKA_AUTO_COMMIT.getCode());
                }
                //最后取mcc默认配置
                return kafkaDefaultConsumerConfig.getAutoCommit();
            case KAFKA_MANUALCOMMIT_FAILSTRATEGY:
                //先取注解值
                KafkaManualCommitFailStrategyEnum kafkaManualCommitFailStrategyEnum = annotation.manualCommitFailStrategy();
                if (!KafkaManualCommitFailStrategyEnum.NOP.equals(kafkaManualCommitFailStrategyEnum)) {
                    return kafkaManualCommitFailStrategyEnum;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_MANUALCOMMIT_FAILSTRATEGY.getCode()) != null) {
                    String manualCommitFailStrategy = (String) kafkaConsumerConfig.get(KAFKA_MANUALCOMMIT_FAILSTRATEGY.getCode());
                    return KafkaManualCommitFailStrategyEnum.getByCode(manualCommitFailStrategy);
                }
                //最后取mcc默认配置
                return KafkaManualCommitFailStrategyEnum.getByCode(kafkaDefaultConsumerConfig.getManualCommitFailStrategy());
            case KAFKA_COMMIT_TYPE:
                //先取注解值
                KafkaCommitTypeEnum kafkaCommitTypeEnum = annotation.commitType();
                if (!KafkaCommitTypeEnum.NOP.equals(kafkaCommitTypeEnum)) {
                    return kafkaCommitTypeEnum;
                }

                //再取xml配置
                if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_COMMIT_TYPE.getCode()) != null) {
                    String commitType = (String) kafkaConsumerConfig.get(KAFKA_COMMIT_TYPE.getCode());
                    return KafkaCommitTypeEnum.getByCode(commitType);
                }
                //最后取mcc默认配置
                return KafkaCommitTypeEnum.getByCode(kafkaDefaultConsumerConfig.getCommitType());
            default:
                throw new KafkaConfigSettingException(kafkaConsumerConfigEnum + "config not found");

        }

    }

}
