package com.monitor.support;

/**
 * 排班
 *
 * @author summer
 */
public enum ScheduleEnum {

    /**
     *
     */
    ONE_CLASS("1"),
    /**
     *
     */
    SECOND_CLASS("2"),
    /**
     *
     */
    THREE_CLASSE("3");

    private String code;

    private ScheduleEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}