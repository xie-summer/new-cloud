package com.weimob.mengdian.soa.kafka.producer.config;

import com.weimob.mengdian.soa.kafka.annotation.KafkaProduced;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

import static com.weimob.mengdian.soa.kafka.model.enums.KafkaProducerConfigEnum.*;


/**
 * @Author chenwp
 * @Date 2017-07-16 16:57
 * @Company WeiMob
 * @Description
 */
public class KafkaProducerConfig {
    private KafkaProduced kafkaProduced;
    private Properties kafkaProducerConfig;
    private KafkaDefaultProducerConfig kafkaDefaultProducerConfig;

    public KafkaProducerConfig(KafkaProduced kafkaProduced, Properties kafkaProducerConfig, KafkaDefaultProducerConfig kafkaDefaultProducerConfig) {
        this.kafkaProduced = kafkaProduced;
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.kafkaDefaultProducerConfig = kafkaDefaultProducerConfig;
    }

    @Setter
    private String acks;
    @Setter
    private Integer retries;
    @Setter
    private Integer batchSize;
    @Setter
    private Integer lingerMs;
    @Setter
    private String bootstrapServers;
    @Setter
    private Long bufferMemory;
    @Setter
    private String keySerializer;
    @Setter
    private String valueSerializer;

    public String getAcks() {
        //先取注解值
        String acks = kafkaProduced.acks();
        if (StringUtils.isNotBlank(acks)) {
            return acks;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_ACKS.getCode()) != null) {
            return (String) kafkaProducerConfig.get(KAFKA_ACKS.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getAcks();
    }

    public Integer getRetries() {
        //先取注解值
        int retries = kafkaProduced.retries();
        if (retries != KafkaConstant.NUMBER_DEFAULT) {
            return retries;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_RETRIES.getCode()) != null) {
            String strRetries = (String) kafkaProducerConfig.get(KAFKA_RETRIES.getCode());
            return Integer.valueOf(strRetries);
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getRetries();
    }

    public Integer getBatchSize() {
        //先取注解值
        int batchSize = kafkaProduced.batchSize();
        if (batchSize != KafkaConstant.NUMBER_DEFAULT) {
            return batchSize;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_BATCH_SIZE.getCode()) != null) {
            String strBatchSize = (String) kafkaProducerConfig.get(KAFKA_BATCH_SIZE.getCode());
            return Integer.valueOf(strBatchSize);
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getBatchSize();
    }

    public String getBootstrapServers() {
        //先取注解值
        String bootstrapServers = kafkaProduced.bootstrapServers();
        if (StringUtils.isNotBlank(bootstrapServers)) {
            return bootstrapServers;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_BOOTSTRAP_SERVERS.getCode()) != null) {
            return (String) kafkaProducerConfig.get(KAFKA_BOOTSTRAP_SERVERS.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getBootstrapServers();
    }

    public Long getBufferMemory() {
        //先取注解值
        long bufferMemory = kafkaProduced.bufferMemory();
        if (bufferMemory != KafkaConstant.NUMBER_DEFAULT) {
            return bufferMemory;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_BUFFER_MEMORY.getCode()) != null) {
            String strBufferMemory = (String) kafkaProducerConfig.get(KAFKA_BUFFER_MEMORY.getCode());
            return Long.valueOf(strBufferMemory);
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getBufferMemory();
    }

    public String getKeySerializer() {
        //先取注解值
        String keySerializer = kafkaProduced.keySerializer();
        if (StringUtils.isNotBlank(keySerializer)) {
            return keySerializer;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_KEY_SERIALIZER.getCode()) != null) {
            return (String) kafkaProducerConfig.get(KAFKA_KEY_SERIALIZER.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getKeySerializer();
    }

    public String getValueSerializer() {
        //先取注解值
        String valueSerializer = kafkaProduced.valueSerializer();
        if (StringUtils.isNotBlank(valueSerializer)) {
            return valueSerializer;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_VALUE_SERIALIZER.getCode()) != null) {
            return (String) kafkaProducerConfig.get(KAFKA_VALUE_SERIALIZER.getCode());
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getValueSerializer();
    }

    public Integer getLingerMs() {
        //先取注解值
        int lingerMs = kafkaProduced.lingerMs();
        if (lingerMs != KafkaConstant.NUMBER_DEFAULT) {
            return lingerMs;
        }

        //再取xml配置
        if (kafkaProducerConfig != null && kafkaProducerConfig.get(KAFKA_LINGER_MS.getCode()) != null) {
            String strLingerMs = (String) kafkaProducerConfig.get(KAFKA_LINGER_MS.getCode());
            return Integer.valueOf(strLingerMs);
        }
        //最后取mcc默认配置
        return kafkaDefaultProducerConfig.getLingerMs();
    }
}
