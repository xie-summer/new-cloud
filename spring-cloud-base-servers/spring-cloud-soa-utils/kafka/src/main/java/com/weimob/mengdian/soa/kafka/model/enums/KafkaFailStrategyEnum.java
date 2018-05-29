package com.weimob.mengdian.soa.kafka.model.enums;

/**
 * @Author chenwp
 * @Date 2017-07-04 15:56
 * @Company WeiMob
 * @Description
 */
public enum KafkaFailStrategyEnum {
    NOP("nop"),
    RETRY("retry"),
    DISCARD("discard");

    private String code;

    KafkaFailStrategyEnum(String code) {
        this.code = code;
    }

    public static KafkaFailStrategyEnum getByCode(String code) {
        for (KafkaFailStrategyEnum kafkaFailStrategyEnum : KafkaFailStrategyEnum.values()) {
            if(kafkaFailStrategyEnum.getCode().equalsIgnoreCase(code)){
                return kafkaFailStrategyEnum;
            }
        }
        return RETRY;
    }

    public String getCode() {
        return code;
    }
}
