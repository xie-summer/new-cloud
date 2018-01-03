package com.monitor.model.productionmonitoring;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "panoramic_production_monitoring")
public class PanoramicProductionMonitoring extends BaseObject {
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
     * 磷钙矿耗
     */
    @Column(name = "calcium_phosphate_ore_consumption")
    private Double calciumPhosphateOreConsumption;

    /**
     * 磷钙酸耗
     */
    @Column(name = "calcium_phosphate_acid_consumption")
    private Double calciumPhosphateAcidConsumption;

    /**
     * 磷钙煤耗
     */
    @Column(name = "coal_calcium_phosphate")
    private Double coalCalciumPhosphate;

    public Double getCalciumPowerConsumption() {
        return calciumPowerConsumption;
    }

    public void setCalciumPowerConsumption(Double calciumPowerConsumption) {
        this.calciumPowerConsumption = calciumPowerConsumption;
    }

    /**
     * 普钙电耗
     */

    @Column(name = "calcium_power_consumption")
    private Double calciumPowerConsumption;

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
     * 获取磷钙矿耗
     *
     * @return calcium_phosphate_ore_consumption - 磷钙矿耗
     */
    public Double getCalciumPhosphateOreConsumption() {
        return calciumPhosphateOreConsumption;
    }

    /**
     * 设置磷钙矿耗
     *
     * @param calciumPhosphateOreConsumption 磷钙矿耗
     */
    public void setCalciumPhosphateOreConsumption(Double calciumPhosphateOreConsumption) {
        this.calciumPhosphateOreConsumption = calciumPhosphateOreConsumption;
    }

    /**
     * 获取磷钙酸耗
     *
     * @return calcium_phosphate_acid_consumption - 磷钙酸耗
     */
    public Double getCalciumPhosphateAcidConsumption() {
        return calciumPhosphateAcidConsumption;
    }

    /**
     * 设置磷钙酸耗
     *
     * @param calciumPhosphateAcidConsumption 磷钙酸耗
     */
    public void setCalciumPhosphateAcidConsumption(Double calciumPhosphateAcidConsumption) {
        this.calciumPhosphateAcidConsumption = calciumPhosphateAcidConsumption;
    }

    /**
     * 获取磷钙煤耗
     *
     * @return coal_calcium_phosphate - 磷钙煤耗
     */
    public Double getCoalCalciumPhosphate() {
        return coalCalciumPhosphate;
    }

    /**
     * 设置磷钙煤耗
     *
     * @param coalCalciumPhosphate 磷钙煤耗
     */
    public void setCoalCalciumPhosphate(Double coalCalciumPhosphate) {
        this.coalCalciumPhosphate = coalCalciumPhosphate;
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