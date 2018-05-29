package com.weimob.mengdian.soa.kafka.model;

import com.weimob.mengdian.soa.kafka.consumer.config.KafkaConsumerConfig;
import lombok.*;

import java.io.Serializable;

/**
 * @Author chenwp
 * @Date 2017-07-10 15:35
 * @Company WeiMob
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaTotalConsumeInfo implements Serializable {
    private static final long serialVersionUID = 8679925690899618359L;

    private KafkaConsumerConfig kafkaConsumerConfig;

    /**
     * polling出来的消息总量
     */
    private Integer pollingTotalCount;

    /**
     * 单线程消费最长耗时 ms
     */
    private Long longestConsumeTime;

    /**
     * 已经消费的数量
     */
    private Integer totalConsumeCount;

    /**
     * 已经消耗的时间
     */
    private Long totalConsumeTime;

}
