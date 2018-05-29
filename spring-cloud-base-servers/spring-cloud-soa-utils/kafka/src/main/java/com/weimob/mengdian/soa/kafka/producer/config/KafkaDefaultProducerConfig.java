package com.weimob.mengdian.soa.kafka.producer.config;

import lombok.Data;

/**
 * @Author chenwp
 * @Date 2017-07-16 10:53
 * @Company WeiMob
 * @Description
 */
@Data
public class KafkaDefaultProducerConfig {
    private String acks;
    private Integer retries;
    private Integer batchSize;
    private String bootstrapServers;
    private Long bufferMemory;
    private String keySerializer;
    private String valueSerializer;
    private Integer lingerMs;
}
