package com.weimob.mengdian.soa.kafka.model.enums;

/**
 * @Author chenwp
 * @Date 2017-07-16 13:23
 * @Company WeiMob
 * @Description
 */
public enum  KafkaConsumerConfigEnum {
    KAFKA_BOOTSTRAP_SERVERS("kafka.bootstrap.servers"),
    KAFKA_CONSUMER_TOPIC("kafka.consumer.topic"),
    KAFKA_GROUP_NUMBER("kafka.group.number"),
    KAFKA_GROUP_ID("kafka.group.id"),
    KAFKA_POLLER_NUMBER("kafka.poller.number"),
    KAFKA_CONSUMER_NUMBER("kafka.consumer.number"),
    KAFKA_CONSUMER_STEP("kafka.consumer.step"),
    KAFKA_MAX_POLL_RECORDS(" kafka.max.poll.records"),
    KAFKA_READTIME_MS("kafka.readTime.ms"),
    KAFKA_RETRY_COUNT("kafka.retry.count"),
    KAFKA_RETRYINTERVAL_MS("kafka.retryInterval.ms"),
    KAFKA_CALLONFAILEXCEPTION_RETRYINTERVAL_MS("kafka.callOnFailException.retryInterval.ms"),
    KAFKA_FAILSTRATEGY("kafka.failStrategy"),
    KAFKA_SESSION_TIMEOUT_MS("kafka.session.timeout.ms"),
    KAFKA_HEARTBEAT_INTERVAL_MS("kafka.heartbeat.interval.ms"),
    KAFKA_REQUEST_TIMEOUT_MS("kafka.request.timeout.ms"),
    KAFKA_KEY_DESERIALIZER("kafka.key.deserializer"),
    KAFKA_VALUE_DESERIALIZER("kafka.value.deserializer"),
    KAFKA_IMMEDIATELYEXIT_WHENERROR("kafka.immediatelyExit.whenError"),
    KAFKA_AUTO_COMMIT(" kafka.auto.commit"),
    KAFKA_MANUALCOMMIT_FAILSTRATEGY("kafka.manualCommit.failStrategy"),
    KAFKA_COMMIT_TYPE("kafka.commit.type");

    private String code;

    KafkaConsumerConfigEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
