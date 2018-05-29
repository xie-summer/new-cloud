package com.weimob.mengdian.soa.kafka.consumer.config;

import lombok.Data;

/**
 * @Author chenwp
 * @Date 2017-07-16 11:17
 * @Company WeiMob
 * @Description
 */
@Data
public class KafkaDefaultConsumerConfig {
    private String bootstrapServers;
    private String topic;
    private Integer groupNumber;
    private String groupId;
    private Integer pollerNumber;
    private Integer consumerNumber;
    private Integer consumerStep;
    private Integer maxPollRecords;
    private Integer readTimeMs;
    private Integer retryCount;
    private Integer retryIntervalMs;
    private Integer callOnFailExceptionRetryIntervalMs;
    private String failStrategy;
    private Integer sessionTimeoutMs;
    private Integer heartbeatIntervalMs;
    private Integer requestTimeoutMs;
    private String keyDeserializer;
    private String valueDeserializer;
    private Boolean immediatelyExitWhenError;
    private Boolean autoCommit;
    private String manualCommitFailStrategy;
    private String commitType;
}
