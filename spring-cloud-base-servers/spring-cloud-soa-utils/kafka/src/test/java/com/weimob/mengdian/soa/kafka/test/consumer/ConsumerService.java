package com.weimob.mengdian.soa.kafka.test.consumer;

import com.weimob.mengdian.soa.kafka.annotation.KafkaConsumed;
import com.weimob.mengdian.soa.kafka.consumer.Consumer;
import com.weimob.mengdian.soa.kafka.model.KafkaConsumerMessage;
import com.weimob.mengdian.soa.kafka.model.KafkaPolledMessages;
import com.weimob.mengdian.soa.kafka.test.model.KafkaProducerMessage;
import com.weimob.mengdian.soa.utils.encrypt.JsonUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 测试多个分组 多个partition消费
 *
 * @Author chenwp
 * @Date 2017-06-24 18:20
 * @Company WeiMob
 * @Description
 */
@KafkaConsumed(topic = "cwpTest4Partitions",
        groupId = "cwpTest4Partitions",
        groupNumber = 2,
        pollerNumber = 4,
        consumerStep = 3,
        retryCount = 1)
public class ConsumerService implements Consumer<KafkaProducerMessage> {
    /**
     * 消费方法
     *
     * @param kafkaPolledMessages 从kafka polling到的消息是一个list<String> 长度<=
     * @KafkaConsumed 里面声明的consumerStep值
     */
    @Override
    public void consume(KafkaPolledMessages<KafkaProducerMessage> kafkaPolledMessages) {
        List<KafkaConsumerMessage<KafkaProducerMessage>> kafkaConsumerMessages = kafkaPolledMessages.getMessage();
        if (CollectionUtils.isEmpty(kafkaConsumerMessages)) {
            return;
        }
        System.out.println("获取到的消息长度:" + kafkaConsumerMessages.size());
        for (KafkaConsumerMessage<KafkaProducerMessage> kafkaConsumerMessage : kafkaConsumerMessages) {
            System.out.println(JsonUtils.writeValueAsString(kafkaConsumerMessage));
        }
    }

    /**
     * consume方法失败，并重试次数用完之后调用
     */
    @Override
    public void onFail() {
        System.out.println("do something when failed");
    }
}
