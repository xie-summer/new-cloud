package com.weimob.mengdian.soa.kafka.model;

import lombok.*;

import java.io.Serializable;

/**
 * @Author chenwp
 * @Date 2017-07-10 15:05
 * @Company WeiMob
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaPerThreadConsumeInfo implements Serializable {
    private static final long serialVersionUID = 2906262133080415643L;

    /**
     * 消费耗时
     */
    private Long consumeTime;

    /**
     * 消费记录数量
     */
    private Integer consumeCount;
}
