package com.weimob.mengdian.soa.kafka.test;

import com.weimob.mengdian.soa.kafka.test.consumer.ConsumerService;
import com.weimob.mengdian.soa.kafka.consumer.Consumer;
import com.weimob.mengdian.soa.kafka.utils.KafkaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liuchun on 2017/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-soa-utils-kafka-consumer-test.xml",
})
public class TestKafkaConsumerService {

    @Test
    public void testKafkaConsumer() throws InterruptedException {
        Thread.currentThread().join(60000L);
    }

    @Test
    public void testGenericType() {
        System.out.println(KafkaUtils.getInterfaceGenericType(ConsumerService.class, Consumer.class.getName()).getName());
    }


}
