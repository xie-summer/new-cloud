package com.weimob.mengdian.soa.kafka.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chenwp
 * @Date 2017-06-19 18:23
 * @Company WeiMob
 * @Description kafka返回信息model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class KafkaSendResponse implements Serializable {
    private static final long serialVersionUID = 4980711727394250238L;

    /**
     * 返回码  0:全部成功 1:全部失败 2:部分失败
     */
    private String code;
    /**
     * 错误信息
     */
    private List<KafkaSendFailedMessage> exp;
    /**
     * 消息在kafka中的具体信息
     */
    private List<KafkaReturnModel> data;
}
