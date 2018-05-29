package com.weimob.mengdian.soa.kafka.model.enums;

/**
 * @Author chenwp
 * @Date 2017-07-08 15:17
 * @Company WeiMob
 * @Description
 */
public enum KafkaCommitTypeEnum {
    NOP("nop"),
    SYNC("sync"),
    ASYNC("async");

    private String code;

    KafkaCommitTypeEnum(String code) {
        this.code = code;
    }

    public static KafkaCommitTypeEnum getByCode(String code) {
        for (KafkaCommitTypeEnum kafkaCommitTypeEnum : KafkaCommitTypeEnum.values()) {
            if (kafkaCommitTypeEnum.getCode().equalsIgnoreCase(code)) {
                return kafkaCommitTypeEnum;
            }
        }
        return SYNC;
    }

    public String getCode() {
        return code;
    }
}
