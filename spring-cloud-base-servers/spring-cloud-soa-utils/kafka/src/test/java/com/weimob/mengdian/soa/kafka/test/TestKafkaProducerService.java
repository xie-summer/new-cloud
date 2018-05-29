package com.weimob.mengdian.soa.kafka.test;

import com.weimob.mengdian.soa.kafka.test.producer.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liuchun on 2017/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-soa-utils-kafka-producer-test.xml",
})
public class TestKafkaProducerService {
    @Autowired
    private ProducerService producerService;

    /**
     * 单个partition
     *
     * @throws InterruptedException
     */
    @Test
    public void testKafkaProducerOnePartition() throws InterruptedException {
        producerService.testSendCwpTest();
    }

//
//    /**
//     * 多个partition
//     *
//     * @throws InterruptedException
//     */
//    @Test
//    public void testKafkaProducerFourPartition() throws InterruptedException {
//        producerService.testSendCwpTest4Partitions();
//    }
//
//    /**
//     * 多个生产者
//     */
//
//    @Test
//    public void testMultiKafkaProducer() throws InterruptedException {
//        producerService.testSendCwpTest();
//        producerService.testSendCwpTest4Partitions();
//    }
}
