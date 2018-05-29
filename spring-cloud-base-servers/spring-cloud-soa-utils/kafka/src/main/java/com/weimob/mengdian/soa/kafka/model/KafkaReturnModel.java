package com.weimob.mengdian.soa.kafka.model;

import lombok.*;

import java.io.Serializable;

/**
 * @Author chenwp
 * @Date 2017-06-19 18:44
 * @Company WeiMob
 * @Description kafka具体信息model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaReturnModel implements Serializable {
    private static final long serialVersionUID = 3997845654080297867L;
    /**
     * 包含offset topic partition等信息
     */
    private KafkaMetaData kafkaMetaData;

    /**
     * 唯一id，用于日志跟踪
     */
    private String traceMessageKey;
}
