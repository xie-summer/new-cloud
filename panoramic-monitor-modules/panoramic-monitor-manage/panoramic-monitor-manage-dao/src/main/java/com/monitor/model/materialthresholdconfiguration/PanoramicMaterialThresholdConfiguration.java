package com.monitor.model.materialthresholdconfiguration;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 * 物料上下线配置
 */
@Table(name = "panoramic_material_threshold_configuration")
public class PanoramicMaterialThresholdConfiguration extends BaseObject {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 物料编码
     */
    private String code;

    /**
     * 类别
     */
    private String category;

    /**
     * 上限阈值
     */
    @Column(name = "upper_limit")
    private Double upperLimit;

    /**
     * 下线阈值
     */
    @Column(name = "lower_limit")
    private Double lowerLimit;

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
    private Date uptime;

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
     * 获取物料名称
     *
     * @return name - 物料名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置物料名称
     *
     * @param name 物料名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取类别
     *
     * @return category - 类别
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置类别
     *
     * @param category 类别
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取上限阈值
     *
     * @return upper_limit - 上限阈值
     */
    public Double getUpperLimit() {
        return upperLimit;
    }

    /**
     * 设置上限阈值
     *
     * @param upperLimit 上限阈值
     */
    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    /**
     * 获取下线阈值
     *
     * @return lower_limit - 下线阈值
     */
    public Double getLowerLimit() {
        return lowerLimit;
    }

    /**
     * 设置下线阈值
     *
     * @param lowerLimit 下线阈值
     */
    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
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
     * @return uptime - 更新时间
     */
    public Date getUptime() {
        return uptime;
    }

    /**
     * 设置更新时间
     *
     * @param uptime 更新时间
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
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