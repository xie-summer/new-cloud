package com.monitor.dto.exceptionrecord;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
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

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取报警项
     *
     * @return alarm_item - 报警项
     */
    public String getAlarmItem() {
        return alarmItem;
    }

    /**
     * 设置报警项
     *
     * @param alarmItem 报警项
     */
    public void setAlarmItem(String alarmItem) {
        this.alarmItem = alarmItem;
    }

    /**
     * 获取报警状态
     *
     * @return status - 报警状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置报警状态
     *
     * @param status 报警状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取删除标记（1：未删除；0：删除）
     *
     * @return delete_flag - 删除标记（1：未删除；0：删除）
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除标记（1：未删除；0：删除）
     *
     * @param deleteFlag 删除标记（1：未删除；0：删除）
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 获取报警内容
     *
     * @return alarm_content - 报警内容
     */
    public String getAlarmContent() {
        return alarmContent;
    }

    /**
     * 设置报警内容
     *
     * @param alarmContent 报警内容
     */
    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    /**
     * 获取报警时间
     *
     * @return alarm_time - 报警时间
     */
    public Date getAlarmTime() {
        return alarmTime;
    }

    /**
     * 设置报警时间
     *
     * @param alarmTime 报警时间
     */
    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     * 获取责任人
     *
     * @return associated_person - 责任人
     */
    public String getAssociatedPerson() {
        return associatedPerson;
    }

    /**
     * 设置责任人
     *
     * @param associatedPerson 责任人
     */
    public void setAssociatedPerson(String associatedPerson) {
        this.associatedPerson = associatedPerson;
    }

    /**
     * 获取创建时间
     *
     * @return ctime - 创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置创建时间
     *
     * @param ctime 创建时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取更新时间
     *
     * @return utime - 更新时间
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * 设置更新时间
     *
     * @param utime 更新时间
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }

    /**
     * 获取删除时间
     *
     * @return dtime - 删除时间
     */
    public Date getDtime() {
        return dtime;
    }

    /**
     * 设置删除时间
     *
     * @param dtime 删除时间
     */
    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取责任人日志
     *
     * @return related_person_log - 责任人日志
     */
    public String getRelatedPersonLog() {
        return relatedPersonLog;
    }

    /**
     * 设置责任人日志
     *
     * @param relatedPersonLog 责任人日志
     */
    public void setRelatedPersonLog(String relatedPersonLog) {
        this.relatedPersonLog = relatedPersonLog;
    }

}