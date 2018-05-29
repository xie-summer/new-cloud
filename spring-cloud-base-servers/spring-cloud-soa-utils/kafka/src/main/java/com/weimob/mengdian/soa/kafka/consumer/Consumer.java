package com.weimob.mengdian.soa.kafka.consumer;

import com.weimob.mengdian.soa.kafka.model.KafkaPolledMessages;

/**
 * @Author chenwp
 * @Date 2017-06-21 21:39
 * @Company WeiMob
 * @Description
 */

/**
 * kafka消费者通过扫描实现Consumer接口的类
 * 为它们创建消费线程实现
 */
public interface Consumer<T> {
    /**
     * 消费方法
     *
     * @param kafkaPolledMessages 从kafka polling到的消息是一个list<String>
     *                       长度<=@KafkaConsumed 里面声明的consumerStep值
     */
    void consume(KafkaPolledMessages<T> kafkaPolledMessages) throws InterruptedException;

    /**
     * consume方法失败，并重试次数用完之后调用
     */
    void onFail();
}
