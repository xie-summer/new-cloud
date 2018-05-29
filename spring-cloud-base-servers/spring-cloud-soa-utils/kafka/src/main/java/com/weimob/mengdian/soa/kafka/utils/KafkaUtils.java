package com.weimob.mengdian.soa.kafka.utils;

import com.google.common.collect.Lists;
import com.weimob.mengdian.soa.kafka.model.KafkaConsumerMessage;
import com.weimob.mengdian.soa.kafka.model.KafkaMetaData;
import com.weimob.mengdian.soa.kafka.model.KafkaPolledMessages;
import com.weimob.mengdian.soa.kafka.model.KafkaSendMessage;
import com.weimob.mengdian.soa.utils.encrypt.JsonUtils;
import com.weimob.mengdian.soa.utils.external.TomcatUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-06-20 13:36
 * @Company WeiMob
 * @Description
 */
@Slf4j
public class KafkaUtils {
    /**
     * 判断一个object是否是集合类或者数组类
     */
    public static boolean isCollectionOrArray(Object object) {
        if (object instanceof Collection) {
            return true;
        }
        if (object.getClass().isArray()) {
            return true;
        }
        return false;
    }

    /**
     * 如果object是集合类或者数组类，将object转换为collection，否则返回null
     */
    public static Collection getCollectionFromObject(Object object) {
        if (object instanceof Collection) {
            return (Collection) object;
        }
        if (object.getClass().isArray()) {
            int length = Array.getLength(object);
            List<Object> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                Object value = Array.get(object, i);
                list.add(value);
            }
            return list;
        }
        return Collections.singletonList(object);
    }

    public static String getIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("can not get sender ip", e);
        }
        return "unknow ip";
    }

    public static String getTomcatPort() {
        String port = "";
        try {
            port = String.valueOf(TomcatUtil.getHttpPort("HTTP/1.1", "http"));
        } catch (Exception e) {
            log.error("can not get sender tomcat port", e);
        }
        return port;
    }

    /**
     * 获取类指定接口的泛型类型
     */
    public static Class<?> getInterfaceGenericType(Class<?> clazz, String interfaceName) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        Type SpecifyGenericInterface = null;
        for (Type genericInterface : genericInterfaces) {
            Class<?> rawClass = (Class) ((ParameterizedType) genericInterface).getRawType();
            if (interfaceName.equals(rawClass.getName())) {
                SpecifyGenericInterface = genericInterface;
                break;
            }
        }

        if (SpecifyGenericInterface != null) {
            Type actualTypeArgument = ((ParameterizedType) SpecifyGenericInterface).getActualTypeArguments()[0];
            return (Class) actualTypeArgument;
        }

        return null;
    }

    public static List<KafkaConsumerMessage> buildKafkaConsumerMessageList(List<ConsumerRecord<String, String>> consumerRecords) {
        List<KafkaConsumerMessage> kafkaConsumerMessageList = Lists.newArrayList();
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            KafkaConsumerMessage kafkaConsumerMessage = new KafkaConsumerMessage<>();
            kafkaConsumerMessage.setOffset(consumerRecord.offset());
            kafkaConsumerMessage.setPartition(consumerRecord.partition());
            kafkaConsumerMessage.setTopic(consumerRecord.topic());
            kafkaConsumerMessageList.add(kafkaConsumerMessage);
        }
        return kafkaConsumerMessageList;
    }

    public static <T> KafkaPolledMessages<T> buildKafkaConsumerMessageList(List<ConsumerRecord<String, String>> consumerRecords, Class<T> genericClass) {
        KafkaPolledMessages<T> kafkaPolledMessages = new KafkaPolledMessages<>();
        List<KafkaConsumerMessage<T>> kafkaConsumerMessageList = Lists.newArrayList();
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            KafkaConsumerMessage<T> kafkaConsumerMessage = new KafkaConsumerMessage<>();
            kafkaConsumerMessage.setOffset(consumerRecord.offset());
            kafkaConsumerMessage.setPartition(consumerRecord.partition());
            kafkaConsumerMessage.setTopic(consumerRecord.topic());
            //先使用封装类KafkaSendMessage进行反序列化
            KafkaSendMessage kafkaSendMessage = JsonUtils.readValue(consumerRecord.value(), KafkaSendMessage.class);
            //判断是否是非kafka-utils产生的消息
            if (!isExtendMessage(kafkaSendMessage)) {
                kafkaConsumerMessage.setServer(kafkaSendMessage.getServer());
                kafkaConsumerMessage.setProduceTimeStamp(kafkaSendMessage.getTimeStamp());
                kafkaConsumerMessage.setValue(JsonUtils.readValue(JsonUtils.writeValueAsString(kafkaSendMessage.getData()), genericClass));
            } else { // 外部消息使用用户定义的接收类进行反序列化
                kafkaConsumerMessage.setValue(JsonUtils.readValue(consumerRecord.value(), genericClass));
            }

            kafkaConsumerMessageList.add(kafkaConsumerMessage);
        }
        kafkaPolledMessages.setMessage(kafkaConsumerMessageList);
        return kafkaPolledMessages;
    }


    public static KafkaMetaData buildKafkaMetaData(RecordMetadata recordMetadata) {
        KafkaMetaData kafkaMetaData = new KafkaMetaData();
        kafkaMetaData.setOffset(recordMetadata.offset());
        kafkaMetaData.setTopic(recordMetadata.topic());
        kafkaMetaData.setPartition(recordMetadata.partition());
        return kafkaMetaData;
    }

    /**
     * 是否是非kafka-utils产生的数据
     */
    public static boolean isExtendMessage(KafkaSendMessage kafkaSendMessage) {
        if (kafkaSendMessage == null
                || (kafkaSendMessage.getTimeStamp() == null
                && kafkaSendMessage.getData() == null
                && kafkaSendMessage.getServer() == null)
                ) {
            return true;
        }

        return false;
    }

}
