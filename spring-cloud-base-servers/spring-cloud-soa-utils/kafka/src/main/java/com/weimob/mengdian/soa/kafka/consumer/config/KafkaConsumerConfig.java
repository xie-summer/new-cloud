package com.weimob.mengdian.soa.kafka.consumer.config;

import com.weimob.mengdian.soa.kafka.annotation.KafkaConsumed;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaCommitTypeEnum;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaFailStrategyEnum;
import com.weimob.mengdian.soa.kafka.model.enums.KafkaManualCommitFailStrategyEnum;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

import static com.weimob.mengdian.soa.kafka.model.enums.KafkaConsumerConfigEnum.*;

/**
 * @Author chenwp
 * @Date 2017-07-16 15:25
 * @Company WeiMob
 * @Description
 */

public class KafkaConsumerConfig {
    private KafkaConsumed kafkaConsumed;
    private Properties kafkaConsumerConfig;
    private KafkaDefaultConsumerConfig kafkaDefaultConsumerConfig;

    public KafkaConsumerConfig(KafkaConsumed kafkaConsumed, Properties kafkaConsumerConfig, KafkaDefaultConsumerConfig kafkaDefaultConsumerConfig) {
        this.kafkaConsumed = kafkaConsumed;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.kafkaDefaultConsumerConfig = kafkaDefaultConsumerConfig;
    }

    @Setter
    private String bootstrapServers;
    @Setter
    private String topic;
    @Setter
    private Integer groupNumber;
    @Setter
    private String groupId;
    @Setter
    private Integer pollerNumber;
    @Setter
    private Integer consumerNumber;
    @Setter
    private Integer consumerStep;
    @Setter
    private Integer maxPollRecords;
    @Setter
    private Integer readTimeMs;
    @Setter
    private Integer retryCount;
    @Setter
    private Integer retryIntervalMs;
    @Setter
    private Integer callOnFailExceptionRetryIntervalMs;
    @Setter
    private KafkaFailStrategyEnum failStrategy;
    @Setter
    private Integer sessionTimeoutMs;
    @Setter
    private Integer heartbeatIntervalMs;
    @Setter
    private Integer requestTimeoutMs;
    @Setter
    private String keyDeserializer;
    @Setter
    private String valueDeserializer;
    @Setter
    private Boolean immediatelyExitWhenError;
    @Setter
    private Boolean autoCommit;
    @Setter
    private KafkaManualCommitFailStrategyEnum manualCommitFailStrategy;
    @Setter
    private KafkaCommitTypeEnum commitType;


    public String getBootstrapServers() {
        //先取注解值
        String bootstrapServers = kafkaConsumed.bootstrapServers();
        if (StringUtils.isNotBlank(bootstrapServers)) {
            return bootstrapServers;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_BOOTSTRAP_SERVERS.getCode()) != null) {
            return (String) kafkaConsumerConfig.get(KAFKA_BOOTSTRAP_SERVERS.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getBootstrapServers();
    }

    public String getTopic() {
        //先取注解值
        String topic = kafkaConsumed.topic();
        if (StringUtils.isNotBlank(topic)) {
            return topic;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CONSUMER_TOPIC.getCode()) != null) {
            return (String) kafkaConsumerConfig.get(KAFKA_CONSUMER_TOPIC.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getTopic();
    }

    public Integer getGroupNumber() {
        //先取注解值
        int groupNumber = kafkaConsumed.groupNumber();
        if (groupNumber != KafkaConstant.NUMBER_DEFAULT) {
            return groupNumber;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_GROUP_NUMBER.getCode()) != null) {
            String strGroupNumber = (String) kafkaConsumerConfig.get(KAFKA_GROUP_NUMBER.getCode());
            return Integer.valueOf(strGroupNumber);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getGroupNumber();
    }

    public String getGroupId() {
        //先取注解值
        String groupId = kafkaConsumed.groupId();
        if (StringUtils.isNotBlank(groupId)) {
            return groupId;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_GROUP_ID.getCode()) != null) {
            return (String) kafkaConsumerConfig.get(KAFKA_GROUP_ID.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getGroupId();
    }

    public Integer getPollerNumber() {
        //先取注解值
        int pollerNumber = kafkaConsumed.pollerNumber();
        if (pollerNumber != KafkaConstant.NUMBER_DEFAULT) {
            return pollerNumber;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_POLLER_NUMBER.getCode()) != null) {
            String strPollerNumber = (String) kafkaConsumerConfig.get(KAFKA_POLLER_NUMBER.getCode());
            return Integer.valueOf(strPollerNumber);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getPollerNumber();
    }

    public Integer getConsumerNumber() {
        //先取注解值
        int consumerNumber = kafkaConsumed.consumerNumber();
        if (consumerNumber != KafkaConstant.NUMBER_DEFAULT) {
            return consumerNumber;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CONSUMER_NUMBER.getCode()) != null) {
            String strConsumerNumber = (String) kafkaConsumerConfig.get(KAFKA_CONSUMER_NUMBER.getCode());
            return Integer.valueOf(strConsumerNumber);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getConsumerNumber();
    }

    public Integer getConsumerStep() {
        //先取注解值
        int consumerStep = kafkaConsumed.consumerStep();
        if (consumerStep != KafkaConstant.NUMBER_DEFAULT) {
            return consumerStep;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CONSUMER_STEP.getCode()) != null) {
            String strConsumerStep = (String) kafkaConsumerConfig.get(KAFKA_CONSUMER_STEP.getCode());
            return Integer.valueOf(strConsumerStep);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getConsumerStep();
    }

    public Integer getMaxPollRecords() {
        //先取注解值
        int maxPollRecords = kafkaConsumed.maxPollRecords();
        if (maxPollRecords != KafkaConstant.NUMBER_DEFAULT) {
            return maxPollRecords;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_MAX_POLL_RECORDS.getCode()) != null) {
            String strMaxPollRecords =  (String) kafkaConsumerConfig.get(KAFKA_MAX_POLL_RECORDS.getCode());
            return Integer.valueOf(strMaxPollRecords);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getMaxPollRecords();
    }

    public Integer getReadTimeMs() {
        //先取注解值
        int readTimeMs = kafkaConsumed.readTimeMs();
        if (readTimeMs != KafkaConstant.NUMBER_DEFAULT) {
            return readTimeMs;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_READTIME_MS.getCode()) != null) {
            String strReadTimeMs =  (String) kafkaConsumerConfig.get(KAFKA_READTIME_MS.getCode());
            return Integer.valueOf(strReadTimeMs);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getReadTimeMs();
    }

    public Integer getRetryCount() {
        //先取注解值
        int retryCount = kafkaConsumed.retryCount();
        if (retryCount != KafkaConstant.NUMBER_DEFAULT) {
            return retryCount;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_RETRY_COUNT.getCode()) != null) {
            String strRetryCount = (String) kafkaConsumerConfig.get(KAFKA_RETRY_COUNT.getCode());
            return Integer.valueOf(strRetryCount);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getRetryCount();
    }

    public Integer getRetryIntervalMs() {
        //先取注解值
        int retryIntervalMs = kafkaConsumed.retryIntervalMs();
        if (retryIntervalMs != KafkaConstant.NUMBER_DEFAULT) {
            return retryIntervalMs;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_RETRYINTERVAL_MS.getCode()) != null) {
            String strRetryIntervalMs = (String) kafkaConsumerConfig.get(KAFKA_RETRYINTERVAL_MS.getCode());
            return Integer.valueOf(strRetryIntervalMs);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getRetryIntervalMs();
    }

    public Integer getCallOnFailExceptionRetryIntervalMs() {
        //先取注解值
        int callOnFailExceptionRetryIntervalMs = kafkaConsumed.callOnFailExceptionRetryIntervalMs();
        if (callOnFailExceptionRetryIntervalMs != KafkaConstant.NUMBER_DEFAULT) {
            return callOnFailExceptionRetryIntervalMs;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_CALLONFAILEXCEPTION_RETRYINTERVAL_MS.getCode()) != null) {
            String strCallOnFailExceptionRetryIntervalMs = (String) kafkaConsumerConfig.get(KAFKA_CALLONFAILEXCEPTION_RETRYINTERVAL_MS.getCode());
            return Integer.valueOf(strCallOnFailExceptionRetryIntervalMs);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getCallOnFailExceptionRetryIntervalMs();
    }

    public KafkaFailStrategyEnum getFailStrategy() {
        //先取注解值
        KafkaFailStrategyEnum kafkaFailStrategyEnum = kafkaConsumed.failStrategy();
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
    }

    public Integer getSessionTimeoutMs() {
        //先取注解值
        int sessionTimeoutMs = kafkaConsumed.sessionTimeoutMs();
        if (sessionTimeoutMs != KafkaConstant.NUMBER_DEFAULT) {
            return sessionTimeoutMs;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_SESSION_TIMEOUT_MS.getCode()) != null) {
            String strSessionTimeoutMs = (String) kafkaConsumerConfig.get(KAFKA_SESSION_TIMEOUT_MS.getCode());
            return Integer.valueOf(strSessionTimeoutMs);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getSessionTimeoutMs();
    }

    public Integer getHeartbeatIntervalMs() {
        //先取注解值
        int heartbeatIntervalMs = kafkaConsumed.heartbeatIntervalMs();
        if (heartbeatIntervalMs != KafkaConstant.NUMBER_DEFAULT) {
            return heartbeatIntervalMs;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_HEARTBEAT_INTERVAL_MS.getCode()) != null) {
            String strHeartbeatIntervalMs = (String) kafkaConsumerConfig.get(KAFKA_HEARTBEAT_INTERVAL_MS.getCode());
            return Integer.valueOf(strHeartbeatIntervalMs);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getHeartbeatIntervalMs();
    }

    public Integer getRequestTimeoutMs() {
        //先取注解值
        int requestTimeoutMs = kafkaConsumed.requestTimeoutMs();
        if (requestTimeoutMs != KafkaConstant.NUMBER_DEFAULT) {
            return requestTimeoutMs;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_REQUEST_TIMEOUT_MS.getCode()) != null) {
            String strRequestTimeoutMs = (String) kafkaConsumerConfig.get(KAFKA_REQUEST_TIMEOUT_MS.getCode());
            return Integer.valueOf(strRequestTimeoutMs);
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getRequestTimeoutMs();
    }

    public String getKeyDeserializer() {
        //先取注解值
        String keyDeserializer = kafkaConsumed.keyDeserializer();
        if (StringUtils.isNotBlank(keyDeserializer)) {
            return keyDeserializer;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_KEY_DESERIALIZER.getCode()) != null) {
            return (String) kafkaConsumerConfig.get(KAFKA_KEY_DESERIALIZER.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getKeyDeserializer();
    }

    public String getValueDeserializer() {
        //先取注解值
        String valueDeserializer = kafkaConsumed.valueDeserializer();
        if (StringUtils.isNotBlank(valueDeserializer)) {
            return valueDeserializer;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_VALUE_DESERIALIZER.getCode()) != null) {
            return (String) kafkaConsumerConfig.get(KAFKA_VALUE_DESERIALIZER.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getValueDeserializer();
    }

    public Boolean getImmediatelyExitWhenError() {
        //先取注解值
        boolean immediatelyExitWhenError = kafkaConsumed.immediatelyExitWhenError();
        if (immediatelyExitWhenError) {
            return true;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_IMMEDIATELYEXIT_WHENERROR.getCode()) != null) {
            return (boolean) kafkaConsumerConfig.get(KAFKA_IMMEDIATELYEXIT_WHENERROR.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getImmediatelyExitWhenError();
    }

    public Boolean getAutoCommit() {
        //先取注解值
        boolean autoCommit = kafkaConsumed.autoCommit();
        if (!autoCommit) {
            return false;
        }

        //再取xml配置
        if (kafkaConsumerConfig != null && kafkaConsumerConfig.get(KAFKA_AUTO_COMMIT.getCode()) != null) {
            return (boolean) kafkaConsumerConfig.get(KAFKA_AUTO_COMMIT.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultConsumerConfig.getAutoCommit();
    }

    public KafkaManualCommitFailStrategyEnum getManualCommitFailStrategy() {
        //先取注解值
        KafkaManualCommitFailStrategyEnum kafkaManualCommitFailStrategyEnum = kafkaConsumed.manualCommitFailStrategy();
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
    }

    public KafkaCommitTypeEnum getCommitType() {
        //先取注解值
        KafkaCommitTypeEnum kafkaCommitTypeEnum = kafkaConsumed.commitType();
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
    }
}
