package com.monitor.dto.exceptionrecord;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Data
public class PanoramicExceptionRecordDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 报警项
     */
    private String alarmItem;

    /**
     * 报警状态（1：正常；0：报警）
     */
    private Integer status;

    /**
     * 删除标记（1：未删除；0：删除）
     */
    private Integer deleteFlag;

    /**
     * 报警内容
     */
    private String alarmContent;

    /**
     * 报警时间
     */
    private Date alarmTime;

    /**
     * 责任人
     */
    private String associatedPerson;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;

    /**
     * 删除时间
     */
    private Date dtime;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 责任人日志
     */
    private String relatedPersonLog;

}