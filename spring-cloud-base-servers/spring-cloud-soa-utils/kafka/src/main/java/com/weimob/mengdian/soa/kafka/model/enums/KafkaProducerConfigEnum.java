package com.weimob.mengdian.soa.kafka.model.enums;

/**
 * @Author chenwp
 * @Date 2017-07-16 17:27
 * @Company WeiMob
 * @Description
 */
public enum KafkaProducerConfigEnum {
    KAFKA_BOOTSTRAP_SERVERS("kafka.bootstrap.servers"),
    KAFKA_ACKS("kafka.acks"),
    KAFKA_RETRIES("kafka.retries"),
    KAFKA_BATCH_SIZE("kafka.batch.size"),
    KAFKA_LINGER_MS("kafka.linger.ms"),
    KAFKA_BUFFER_MEMORY("kafka.buffer.memory"),
    KAFKA_KEY_SERIALIZER("kafka.key.serializer"),
    KAFKA_VALUE_SERIALIZER("kafka.value.serializer");

    private String code;

    KafkaProducerConfigEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
