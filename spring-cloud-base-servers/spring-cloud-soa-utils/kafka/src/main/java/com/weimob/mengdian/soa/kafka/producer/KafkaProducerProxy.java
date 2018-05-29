package com.weimob.mengdian.soa.kafka.producer;

import com.google.common.collect.Lists;
import com.weimob.mengdian.soa.cat.CatTransaction;
import com.weimob.mengdian.soa.kafka.Compatible;
import com.weimob.mengdian.soa.kafka.annotation.KafkaProduced;
import com.weimob.mengdian.soa.kafka.constants.KafkaConstant;
import com.weimob.mengdian.soa.kafka.model.*;
import com.weimob.mengdian.soa.kafka.model.exception.KafkaCallbackException;
import com.weimob.mengdian.soa.kafka.producer.config.KafkaDefaultProducerConfig;
import com.weimob.mengdian.soa.kafka.producer.config.KafkaProducerConfig;
import com.weimob.mengdian.soa.kafka.utils.KafkaUtils;
import com.weimob.mengdian.soa.utils.encrypt.JsonUtils;
import com.weimob.mengdian.soa.utils.general.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @Author chenwp
 * @Date 2017-06-27 16:05
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaProducerProxy {

    /*kafka生产者默认配置 维护在mcc*/
    private KafkaDefaultProducerConfig kafkaDefaultProducerConfig;
    /*生成者服务ip*/
    private final String senderSever;
    /*生产者本地配置，如果设置则覆盖默认配置*/
    private final Properties kafkaProducerConfig;
    /*生产者接口*/
    private final Class<?> producerClass;
    /*生产者方法*/
    private String methodName;
    private String topic;

    /**
     * kafka 生产者客户端
     */
    private Producer<String, String> producer;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public KafkaProducerProxy(String senderSever, Properties kafkaProducerConfig, KafkaDefaultProducerConfig kafkaDefaultConsumerConfig, Class<?> producerClass) {
        this.senderSever = senderSever;
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.producerClass = producerClass;
        this.kafkaDefaultProducerConfig = kafkaDefaultConsumerConfig;

        KafkaProduced kafkaProduced = producerClass.getAnnotation(KafkaProduced.class);
        KafkaProducerConfig mergeKafkaProducerConfig = new KafkaProducerConfig(kafkaProduced, kafkaProducerConfig, kafkaDefaultProducerConfig);
        this.producer = createProducer(mergeKafkaProducerConfig);
    }

    public KafkaSendResponse invoke(Object arg) {
        String classSimpleName = ClassUtils.simpleName(producerClass.getName());
        String catName = classSimpleName + "_" + methodName + "_" + topic;

        try {
            return doCreate(arg, producerClass, catName);
        } catch (Exception e) {
            throw e;
        }
    }

    private KafkaSendResponse doCreate(Object arg, Class<?> producerClass, String catName) {
        if (arg == null) {
            KafkaSendFailedMessage kafkaSendFailedMessage = KafkaSendFailedMessage.of("arg is null", null);
            return KafkaSendResponse.of(KafkaConstant.FAIL, Lists.<KafkaSendFailedMessage>newArrayList(kafkaSendFailedMessage), null);
        }
        return send(arg, catName, this.producer);
    }

    /**
     * 创建kafka生产者客户端
     */
    private Producer<String, String> createProducer(KafkaProducerConfig kafkaProducerConfig) {
        Properties props = new Properties();
        props.put(KafkaConstant.BOOTSTRAP_SERVERS, kafkaProducerConfig.getBootstrapServers());
        props.put(KafkaConstant.ACKS, kafkaProducerConfig.getAcks());
        props.put(KafkaConstant.RETRIES, kafkaProducerConfig.getRetries());
        props.put(KafkaConstant.BATCH_SIZE, kafkaProducerConfig.getBatchSize());
        props.put(KafkaConstant.LINGER_MS, kafkaProducerConfig.getLingerMs());
        props.put(KafkaConstant.BUFFER_MEMORY, kafkaProducerConfig.getBufferMemory());
        props.put(KafkaConstant.KEY_SERIALIZER, kafkaProducerConfig.getKeySerializer());
        props.put(KafkaConstant.VALUE_SERIALIZER, kafkaProducerConfig.getValueSerializer());
        return new KafkaProducer<>(props);
    }


    private KafkaSendResponse send(Object arg, String catName, Producer<String, String> producer) {

        //发送主题
        KafkaSendResponse kafkaSendResponse = null;
        //参数是collection或数组 批量发送kafka
        if (KafkaUtils.isCollectionOrArray(arg)) {
            Collection collectionFromObject = KafkaUtils.getCollectionFromObject(arg);
            kafkaSendResponse = sendBatch(collectionFromObject, producer, topic, catName);

        } else {
            kafkaSendResponse = sendSingle(arg, producer, topic, catName);
        }
        return kafkaSendResponse;

    }

    /**
     * 单个发送kafka
     *
     * @param originalMessage 原始信息
     * @param kafkaProducer   kafka生产者
     * @param topic           发送主题
     * @return 发送结果
     */
    private KafkaSendResponse sendSingle(Object originalMessage, Producer<String, String> kafkaProducer, String topic, String catName) {
        Long timeStamp = System.currentTimeMillis();
        KafkaSendMessage kafkaSendMessage = KafkaSendMessage.of(senderSever, timeStamp, originalMessage);
        String traceMessageKey = UUID.randomUUID().toString();
        String message = JsonUtils.writeValueAsString(kafkaSendMessage);
        KafkaSendResponse kafkaSendResponse = null;
        val catKafkaProducerSend = new CatTransaction(KafkaConstant.KAFKA_PRODUCER_SEND, catName);
        try {
            Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<>(topic, traceMessageKey, message));
            RecordMetadata metadata = future.get();
            if (metadata != null) {
                KafkaMetaData kafkaMetaData = KafkaUtils.buildKafkaMetaData(metadata);
                KafkaReturnModel kafkaReturnModel = KafkaReturnModel.of(kafkaMetaData, traceMessageKey);
                kafkaSendResponse = KafkaSendResponse.of(KafkaConstant.SUCCESS, Lists.<KafkaSendFailedMessage>newArrayList(), Lists.<KafkaReturnModel>newArrayList(kafkaReturnModel));
                catKafkaProducerSend.success();
            } else {
                catKafkaProducerSend.error(new KafkaCallbackException("send message to kafka failed,kafka not response!"));
            }
        } catch (Exception e) {
            catKafkaProducerSend.error(e);
            KafkaSendFailedMessage kafkaSendFailedMessage = KafkaSendFailedMessage.of(JsonUtils.writeValueAsString(e), JsonUtils.writeValueAsString(originalMessage));
            kafkaSendResponse = KafkaSendResponse.of(KafkaConstant.FAIL, Lists.<KafkaSendFailedMessage>newArrayList(kafkaSendFailedMessage), Lists.<KafkaReturnModel>newArrayList());
        }
        return kafkaSendResponse;
    }

    /**
     * 批量发送kafka
     *
     * @param originalMessageList 原始信息集合
     * @param kafkaProducer       kafka生产者
     * @param topic               发送主题
     * @return 发送结果
     */
    private KafkaSendResponse sendBatch(Collection originalMessageList, Producer<String, String> kafkaProducer, String topic, String catName) {
        List<KafkaReturnModel> successList = Lists.newArrayList();
        List<KafkaSendFailedMessage> failList = Lists.newArrayList();
        for (Object originalMessage : originalMessageList) {
            String message;
            String traceMessageKey = UUID.randomUUID().toString();
            //如果消息类继承了兼容接口则不组装服务ip以及发送时间信息
            if (originalMessage instanceof Compatible) {
                message = JsonUtils.writeValueAsString(originalMessage);
            } else {
                Long timeStamp = System.currentTimeMillis();
                KafkaSendMessage kafkaSendMessage = KafkaSendMessage.of(senderSever, timeStamp, originalMessage);
                message = JsonUtils.writeValueAsString(kafkaSendMessage);
            }

            if (StringUtils.isBlank(message)) {
                log.error("kafka topic:" + topic + "produce a empty message");
                continue;
            }

            Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<>(topic, traceMessageKey, message));
            val catKafkaProducerSend = new CatTransaction(KafkaConstant.KAFKA_PRODUCER_SEND, catName);
            try {
                RecordMetadata metadata = future.get();
                if (metadata != null) {
                    KafkaMetaData kafkaMetaData = KafkaUtils.buildKafkaMetaData(metadata);
                    KafkaReturnModel kafkaReturnModel = KafkaReturnModel.of(kafkaMetaData, traceMessageKey);
                    successList.add(kafkaReturnModel);
                    catKafkaProducerSend.success();
                } else {
                    catKafkaProducerSend.error(new KafkaCallbackException("send message to kafka failed,kafka not response!"));
                }
            } catch (Exception e) {
                catKafkaProducerSend.error(e);
                KafkaSendFailedMessage kafkaSendFailedMessage = KafkaSendFailedMessage.of(JsonUtils.writeValueAsString(e), JsonUtils.writeValueAsString(originalMessage));
                failList.add(kafkaSendFailedMessage);
            }
        }
        KafkaSendResponse kafkaSendResponse = null;
        if (CollectionUtils.isEmpty(failList)) {
            kafkaSendResponse = KafkaSendResponse.of(KafkaConstant.SUCCESS, failList, successList);
        } else if (CollectionUtils.isEmpty(successList)) {
            kafkaSendResponse = KafkaSendResponse.of(KafkaConstant.FAIL, failList, successList);
        } else {
            kafkaSendResponse = KafkaSendResponse.of(KafkaConstant.SOME_FAIL, failList, successList);
        }
        return kafkaSendResponse;
    }
}
