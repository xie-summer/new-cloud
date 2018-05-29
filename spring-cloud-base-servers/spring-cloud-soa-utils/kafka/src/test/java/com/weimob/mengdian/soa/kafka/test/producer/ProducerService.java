package com.weimob.mengdian.soa.kafka.test.producer;

import com.google.common.collect.Lists;
import com.weimob.mengdian.soa.kafka.model.KafkaSendResponse;
import com.weimob.mengdian.soa.kafka.test.model.KafkaProducerMessage;
import com.weimob.mengdian.soa.utils.encrypt.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-06-24 13:50
 * @Company WeiMob
 * @Description
 */
@Service
public class ProducerService {
    @Autowired
    private Producer producer;

    public void testSendCwpTest() {
        List<KafkaProducerMessage> producerMessageList = Lists.newArrayList();
        KafkaProducerMessage producerMessage1 = new KafkaProducerMessage();
        producerMessage1.setAge(1);
        producerMessage1.setName("xiaoming");
        producerMessage1.setId(1L);
        producerMessage1.setCommitTime(System.currentTimeMillis() + 20000);

//        KafkaProducerMessage producerMessage2 = new KafkaProducerMessage();
//        producerMessage2.setAge(2);
//        producerMessage2.setName("xiaohong");
//        producerMessage2.setId(2L);
//        producerMessage2.setCommitTime(System.currentTimeMillis() + 300000);

        producerMessageList.add(producerMessage1);
//        producerMessageList.add(producerMessage2);
        KafkaSendResponse send = producer.sendCwpTest(producerMessageList);
        System.out.println(JsonUtils.writeValueAsString(send));
    }


//    public void testSendCwpTest4Partitions() {
//        List<KafkaProducerMessage> producerMessageList = Lists.newArrayList();
//        KafkaProducerMessage producerMessage1 = new KafkaProducerMessage();
//        producerMessage1.setAge(1);
//        producerMessage1.setName("xiaoming");
//        producerMessage1.setId(1L);
//        producerMessage1.setCommitTime(System.currentTimeMillis() - 300000);
//
//
//        KafkaProducerMessage producerMessage2 = new KafkaProducerMessage();
//        producerMessage2.setAge(2);
//        producerMessage2.setName("xiaohong");
//        producerMessage2.setId(2L);
//        producerMessage2.setCommitTime(System.currentTimeMillis() - 200000);
//
//        KafkaProducerMessage producerMessage3 = new KafkaProducerMessage();
//        producerMessage3.setAge(3);
//        producerMessage3.setName("xiaobai");
//        producerMessage3.setId(3L);
//        producerMessage3.setCommitTime(System.currentTimeMillis() - 100000);
//
//        KafkaProducerMessage producerMessage4 = new KafkaProducerMessage();
//        producerMessage4.setAge(4);
//        producerMessage4.setName("xiaohei");
//        producerMessage4.setId(4L);
//        producerMessage4.setCommitTime(System.currentTimeMillis() + 30000);
//
//        producerMessageList.add(producerMessage1);
//        producerMessageList.add(producerMessage2);
//        producerMessageList.add(producerMessage3);
//        producerMessageList.add(producerMessage4);
//
//        for (int i = 0; i < 1; i++) {
//            KafkaSendResponse send = producer.sendCwpTest4Partitions(producerMessageList);
//        }
////        System.out.println(JsonUtils.writeValueAsString(send));
//    }
}
