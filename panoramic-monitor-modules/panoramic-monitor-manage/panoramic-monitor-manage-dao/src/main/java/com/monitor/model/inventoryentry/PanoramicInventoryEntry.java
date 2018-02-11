package com.monitor.model.inventoryentry;

import com.cloud.model.BaseObject;
import com.monitor.support.ScheduleEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 人工录入
 *
 * @author summer
 */
@Table(name = "panoramic_inventory_entry")
public class PanoramicInventoryEntry extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 产品名
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 物料编码
     */
    private String code;

    /**
     * 进出库类型（1：进库; 0：出库）
     */
    @Column(name = "in_or_out")
    private Integer inOrOut;
    /**
     * 排班
     */
    private String schedule;
    /**
     * 单位
     */
    private String unit;

    /**
     * 删除标记（1：有效；0：删除）
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
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
     * 工厂ID
     */
    @Column(name = "f_id")
    private String fId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取产品名
     *
     * @return name - 产品名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置产品名
     *
     * @param name 产品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取物料编码
     *
     * @return code - 物料编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置物料编码
     *
     * @param code 物料编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取进出库类型（1：进库; 0：出库）
     *
     * @return in_or_out - 进出库类型（1：进库; 0：出库）
     */
    public Integer getInOrOut() {
        return inOrOut;
    }

    /**
     * 设置进出库类型（1：进库; 0：出库）
     *
     * @param inOrOut 进出库类型（1：进库; 0：出库）
     */
    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取删除标记（1：有效；0：删除）
     *
     * @return delete_flag - 删除标记（1：有效；0：删除）
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除标记（1：有效；0：删除）
     *
     * @param deleteFlag 删除标记（1：有效；0：删除）
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
     * 获取排班
     *
     * @return schedule - 排班
     */
    public String getSchedule() {
        if (ScheduleEnum.ONE_CLASS.getCode().equalsIgnoreCase(schedule)) {
            return "早班";
        }
        if (ScheduleEnum.SECOND_CLASS.getCode().equalsIgnoreCase(schedule)) {
            return "中班";
        }
        if (ScheduleEnum.THREE_CLASSE.getCode().equalsIgnoreCase(schedule)) {
            return "晚班";
        }
        return schedule;
    }

    /**
     * 设置排班
     *
     * @param schedule 排班
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * 获取修改时间
     *
     * @return utime - 修改时间
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * 设置修改时间
     *
     * @param utime 修改时间
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
     * 获取工厂ID
     *
     * @return f_id - 工厂ID
     */
    public String getfId() {
        return fId;
    }

    /**
     * 设置工厂ID
     *
     * @param fId 工厂ID
     */
    public void setfId(String fId) {
        this.fId = fId;
    }

    @Override
    public Serializable realId() {
        return null;
    }
}