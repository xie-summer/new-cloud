package com.weimob.mengdian.soa.kafka.model.enums;

/**
 * kafka提交异常采用的策略
 * @Author chenwp
 * @Date 2017-07-06 16:53
 * @Company WeiMob
 * @Description
 */
public enum KafkaManualCommitFailStrategyEnum {
    NOP("nop"),
    /*打印日志*/
    LOG("log"),
    /*打印日志并抛出异常*/
    THROW_EXCEPTION("throwException");

    private String code;

    KafkaManualCommitFailStrategyEnum(String code) {
        this.code = code;
    }

    public static KafkaManualCommitFailStrategyEnum getByCode(String code) {
        for (KafkaManualCommitFailStrategyEnum kafkaManualCommitFailStrategyEnum : KafkaManualCommitFailStrategyEnum.values()) {
            if (kafkaManualCommitFailStrategyEnum.getCode().equals(code)) {
                return kafkaManualCommitFailStrategyEnum;
            }
        }
        return LOG;
    }

    public String getCode() {
        return code;
    }
}
