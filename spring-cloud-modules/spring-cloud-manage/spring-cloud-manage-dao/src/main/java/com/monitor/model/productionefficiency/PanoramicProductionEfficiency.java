package com.monitor.model.productionefficiency;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "panoramic_production_efficiency")
public class PanoramicProductionEfficiency extends BaseObject {
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
     * 班次
     */
    private String flights;

    /**
     * 磷钙产量
     */
    @Column(name = "calcium_phosphate_production")
    private String calciumPhosphateProduction;

    /**
     * 不合格率
     */
    @Column(name = "failure_rate")
    private String failureRate;

    /**
     * 磷钙矿耗
     */
    @Column(name = "calcium_phosphate_ore_consumption")
    private String calciumPhosphateOreConsumption;

    /**
     * 磷矿酸耗
     */
    @Column(name = "calcium_phosphate_acid_consumption")
    private String calciumPhosphateAcidConsumption;

    /**
     * 巡检完成率
     */
    @Column(name = "inspection_completion_rate")
    private String inspectionCompletionRate;

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
     * 获取磷钙产量
     *
     * @return calcium_phosphate_production - 磷钙产量
     */
    public String getCalciumPhosphateProduction() {
        return calciumPhosphateProduction;
    }

    /**
     * 设置磷钙产量
     *
     * @param calciumPhosphateProduction 磷钙产量
     */
    public void setCalciumPhosphateProduction(String calciumPhosphateProduction) {
        this.calciumPhosphateProduction = calciumPhosphateProduction;
    }

    /**
     * 获取不合格率
     *
     * @return failure_rate - 不合格率
     */
    public String getFailureRate() {
        return failureRate;
    }

    /**
     * 设置不合格率
     *
     * @param failureRate 不合格率
     */
    public void setFailureRate(String failureRate) {
        this.failureRate = failureRate;
    }

    /**
     * 获取磷钙矿耗
     *
     * @return calcium_phosphate_ore_consumption - 磷钙矿耗
     */
    public String getCalciumPhosphateOreConsumption() {
        return calciumPhosphateOreConsumption;
    }

    /**
     * 设置磷钙矿耗
     *
     * @param calciumPhosphateOreConsumption 磷钙矿耗
     */
    public void setCalciumPhosphateOreConsumption(String calciumPhosphateOreConsumption) {
        this.calciumPhosphateOreConsumption = calciumPhosphateOreConsumption;
    }

    /**
     * 获取磷矿酸耗
     *
     * @return calcium_phosphate_acid_consumption - 磷矿酸耗
     */
    public String getCalciumPhosphateAcidConsumption() {
        return calciumPhosphateAcidConsumption;
    }

    /**
     * 设置磷矿酸耗
     *
     * @param calciumPhosphateAcidConsumption 磷矿酸耗
     */
    public void setCalciumPhosphateAcidConsumption(String calciumPhosphateAcidConsumption) {
        this.calciumPhosphateAcidConsumption = calciumPhosphateAcidConsumption;
    }

    /**
     * 获取巡检完成率
     *
     * @return inspection_completion_rate - 巡检完成率
     */
    public String getInspectionCompletionRate() {
        return inspectionCompletionRate;
    }

    /**
     * 设置巡检完成率
     *
     * @param inspectionCompletionRate 巡检完成率
     */
    public void setInspectionCompletionRate(String inspectionCompletionRate) {
        this.inspectionCompletionRate = inspectionCompletionRate;
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