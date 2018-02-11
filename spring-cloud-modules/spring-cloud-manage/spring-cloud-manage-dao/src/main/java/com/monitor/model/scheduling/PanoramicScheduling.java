package com.monitor.model.scheduling;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "panoramic_scheduling")
public class PanoramicScheduling extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 班长
     */
    private Integer monitor;

    /**
     * 班次
     */
    private String flights;

    /**
     * 排班时间
     */
    @Column(name = "scheduling_time")
    private Date schedulingTime;

    /**
     * 得分
     */
    private String score;

    /**
     * 删除标记（1：未删除；0：删除）
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

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
     * 获取班长
     *
     * @return monitor - 班长
     */
    public Integer getMonitor() {
        return monitor;
    }

    /**
     * 设置班长
     *
     * @param monitor 班长
     */
    public void setMonitor(Integer monitor) {
        this.monitor = monitor;
    }

    /**
     * 获取班次
     *
     * @return flights - 班次
     */
    public String getFlights() {
        return flights;
    }

    /**
     * 设置班次
     *
     * @param flights 班次
     */
    public void setFlights(String flights) {
        this.flights = flights;
    }

    /**
     * 获取排班时间
     *
     * @return scheduling_time - 排班时间
     */
    public Date getSchedulingTime() {
        return schedulingTime;
    }

    /**
     * 设置排班时间
     *
     * @param schedulingTime 排班时间
     */
    public void setSchedulingTime(Date schedulingTime) {
        this.schedulingTime = schedulingTime;
    }

    /**
     * 获取得分
     *
     * @return score - 得分
     */
    public String getScore() {
        return score;
    }

    /**
     * 设置得分
     *
     * @param score 得分
     */
    public void setScore(String score) {
        this.score = score;
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

    @Override
    public Serializable realId() {
        return null;
    }
}