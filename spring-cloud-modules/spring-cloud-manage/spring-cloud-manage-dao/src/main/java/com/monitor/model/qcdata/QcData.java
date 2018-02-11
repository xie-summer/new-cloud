package com.monitor.model.qcdata;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "qc_data")
public class QcData extends BaseObject {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * value
     */
    private String value;

    /**
     * 时间
     */
    private Date date;

    /**
     * 系统时间
     */
    @Column(name = "sys_date")
    private Date sysDate;

    /**
     * 排班
     */
    @Column(name = "class_no")
    private Integer classNo;

    /**
     * 类型
     */
    private String type;


    /**
     * 例外等级（判断是否为合格的标志）
     */
    @Column(name = "event_level")
    private String eventLevel;
    /**
     * 作业检测项
     */
    @Column(name = "task_item_name")
    private String taskItemName;

    /**
     * @return
     */
    public String getTaskItemName() {
        return taskItemName;
    }

    /**
     * @param taskItemName
     */
    public void setTaskItemName(String taskItemName) {
        this.taskItemName = taskItemName;
    }

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取value
     *
     * @return value - value
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置value
     *
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取时间
     *
     * @return date - 时间
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置时间
     *
     * @param date 时间
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 获取系统时间
     *
     * @return sys_date - 系统时间
     */
    public Date getSysDate() {
        return sysDate;
    }

    /**
     * 设置系统时间
     *
     * @param sysDate 系统时间
     */
    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    /**
     * 获取排班
     *
     * @return class_no - 排班
     */
    public Integer getClassNo() {
        return classNo;
    }

    /**
     * 设置排班
     *
     * @param classNo 排班
     */
    public void setClassNo(Integer classNo) {
        this.classNo = classNo;
    }

    /**
     * eventLevel - 类型
     *
     * @return
     */
    public String getEventLevel() {
        return eventLevel;
    }

    /**
     * @param eventLevel
     */
    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Serializable realId() {
        return null;
    }
}