package com.risk.warning.model;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "panoramic_system_configuration")
public class PanoramicSystemConfiguration extends BaseObject {
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
     * 配置名称
     */
    private String name;

    /**
     * 配置类型1
     */
    private String category1;

    /**
     * 配置类型2
     */
    private String category2;

    /**
     * 配置类型3
     */
    private String category3;

    /**
     * value1
     */
    private String value1;

    /**
     * value2
     */
    private String value2;

    /**
     * value3
     */
    private String value3;

    /**
     * 是否可用（1：可用；0：不可用）
     */
    private Integer status;

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
     * 备忘通知
     */
    private String memo;

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
     * 获取配置名称
     *
     * @return name - 配置名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置配置名称
     *
     * @param name 配置名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取配置类型1
     *
     * @return category1 - 配置类型1
     */
    public String getCategory1() {
        return category1;
    }

    /**
     * 设置配置类型1
     *
     * @param category1 配置类型1
     */
    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    /**
     * 获取配置类型2
     *
     * @return category2 - 配置类型2
     */
    public String getCategory2() {
        return category2;
    }

    /**
     * 设置配置类型2
     *
     * @param category2 配置类型2
     */
    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    /**
     * 获取配置类型3
     *
     * @return category3 - 配置类型3
     */
    public String getCategory3() {
        return category3;
    }

    /**
     * 设置配置类型3
     *
     * @param category3 配置类型3
     */
    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    /**
     * 获取value1
     *
     * @return value1 - value1
     */
    public String getValue1() {
        return value1;
    }

    /**
     * 设置value1
     *
     * @param value1 value1
     */
    public void setValue1(String value1) {
        this.value1 = value1;
    }

    /**
     * 获取value2
     *
     * @return value2 - value2
     */
    public String getValue2() {
        return value2;
    }

    /**
     * 设置value2
     *
     * @param value2 value2
     */
    public void setValue2(String value2) {
        this.value2 = value2;
    }

    /**
     * 获取value3
     *
     * @return value3 - value3
     */
    public String getValue3() {
        return value3;
    }

    /**
     * 设置value3
     *
     * @param value3 value3
     */
    public void setValue3(String value3) {
        this.value3 = value3;
    }

    /**
     * 获取是否可用（1：可用；0：不可用）
     *
     * @return status - 是否可用（1：可用；0：不可用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否可用（1：可用；0：不可用）
     *
     * @param status 是否可用（1：可用；0：不可用）
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
     * 获取备忘通知
     *
     * @return memo - 备忘通知
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备忘通知
     *
     * @param memo 备忘通知
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public Serializable realId() {
        return null;
    }
}