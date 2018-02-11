package com.monitor.support;

/**
 * @author summer
 */
public enum ExceptionRecordStatusEnum {
    /**
     *
     */
    normal("正常"),
    /**
     *
     */
    high("高"),
    /**
     *
     */
    low("低"),
    /**
     *
     */
    configuration("配置异常");

    private String code;

    private ExceptionRecordStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
