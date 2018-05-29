package com.weimob.mengdian.soa.kafka.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author chenwp
 * @Date 2017-07-04 20:34
 * @Company WeiMob
 * @Description
 */
@Data
public class KafkaConsumerMessage<T> implements Serializable{
    private static final long serialVersionUID = 7878204429838383762L;
    /**
     * kafka主题
     */
    private String topic;
    /**
     * 消息所在分区
     */
    private Integer partition;
    /**
     * 消息所在分区偏移
     */
    private Long offset;

    /**
     * 生产者ip端口号 eg: 127.0.0.1:8080
     */
    private String server;

    /**
     * 插入kafka时间
     */
    private Long produceTimeStamp;

    private T value;
}
