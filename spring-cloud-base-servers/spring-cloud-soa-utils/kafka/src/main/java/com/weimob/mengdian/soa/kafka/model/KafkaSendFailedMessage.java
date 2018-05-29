package com.weimob.mengdian.soa.kafka.model;

import lombok.*;

import java.io.Serializable;

/**
 * 发送kafka失败的返回信息
 * @Author chenwp
 * @Date 2017-06-20 21:08
 * @Company WeiMob
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaSendFailedMessage implements Serializable {
    private static final long serialVersionUID = -3542339458195503262L;
    /**
     * 异常信息
     */
    private String exp;
    /**
     * 原始信息
     */
    private String originalMessage;
}
