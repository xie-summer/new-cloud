package com.weimob.mengdian.soa.kafka.test.producer;

import com.weimob.mengdian.soa.kafka.annotation.KafkaProduced;
import com.weimob.mengdian.soa.kafka.annotation.KafkaTopic;
import com.weimob.mengdian.soa.kafka.model.KafkaSendResponse;
import com.weimob.mengdian.soa.kafka.test.model.KafkaProducerMessage;

import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-06-24 13:48
 * @Company WeiMob
 * @Description
 */

/**
 * kafka生产者扫描带有@KafkaProduced注解的接口类
 * 接口方法必须带有@KafkaTopic,否则初始化验证不通过
 * 接口方法入参支持泛型
 */
@KafkaProduced
public interface Producer {
    @KafkaTopic(topic = "cwpTest")
    KafkaSendResponse sendCwpTest(List<KafkaProducerMessage> producerMessages);

//    @KafkaTopic(topic = "cwpTest4Partitions")
//    KafkaSendResponse sendCwpTest4Partitions(List<KafkaProducerMessage> producerMessages);
}
