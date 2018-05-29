package com.weimob.mengdian.soa.kafka.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by kilob on 2017/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-soa-utils-kafka-consumer-test.xml",
})
public class TestKafkaEach {
    @Test
    public void testEach() throws Exception {
        List<String> topics = Arrays.asList("mengdian.updateGrouponStatus.message");
        Long orderNo = 5069000000436723L;

        Properties props = new Properties();
        props.put("bootstrap.servers", "10.10.96.56:9092,10.10.96.168:9092,10.10.96.49:9092");
        props.put("group.id", "test"+System.currentTimeMillis());
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("request.timeout.ms", "50000");
        props.put("max.partition.fetch.bytes", "1000000");
        props.put("auto.offset.reset", "earliest");
        props.put("heartbeat.interval.ms", "25000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(topics);

        String str = "\"orderNo\":"+orderNo.toString();
        int count = 0;
        WHILE:
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(500);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("current count: "+(++count));
                if (record.value().contains(str)) {
                    System.out.println(record.value());
                    break WHILE;
                }
            }
        }
        System.out.println("finish");
    }
}
