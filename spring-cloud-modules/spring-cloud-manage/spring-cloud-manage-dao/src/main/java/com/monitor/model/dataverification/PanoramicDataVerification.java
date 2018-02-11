package com.monitor.model.dataverification;

import java.util.Date;
import javax.persistence.*;

/**
 * @author summer
 */
@Table(name = "panoramic_data_verification")
public class PanoramicDataVerification {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 时间
     */
    private Date date;

    /**
     * 物料名
     */
    private String name;

    /**
     * 采集计量
     */
    @Column(name = "value_auto")
    private String valueAuto;

    /**
     * 出入库记录量
     */
    @Column(name = "value_manual")
    private Double valueManual;

    /**
     * 偏差
     */
    private Double bias;

    /**
     * ETL查询标识
     */
    private String logo;

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
     * 获取物料名
     *
     * @return name - 物料名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置物料名
     *
     * @param name 物料名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取采集计量
     *
     * @return value_auto - 采集计量
     */
    public String getValueAuto() {
        return valueAuto;
    }

    /**
     * 设置采集计量
     *
     * @param valueAuto 采集计量
     */
    public void setValueAuto(String valueAuto) {
        this.valueAuto = valueAuto;
    }

    /**
     * 获取出入库记录量
     *
     * @return value_manual - 出入库记录量
     */
    public Double getValueManual() {
        return valueManual;
    }

    /**
     * 设置出入库记录量
     *
     * @param valueManual 出入库记录量
     */
    public void setValueManual(Double valueManual) {
        this.valueManual = valueManual;
    }

    /**
     * 获取偏差
     *
     * @return bias - 偏差
     */
    public Double getBias() {
        return bias;
    }

    /**
     * 设置偏差
     *
     * @param bias 偏差
     */
    public void setBias(Double bias) {
        this.bias = bias;
    }

    /**
     * 获取ETL查询标识
     *
     * @return logo - ETL查询标识
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置ETL查询标识
     *
     * @param logo ETL查询标识
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
}