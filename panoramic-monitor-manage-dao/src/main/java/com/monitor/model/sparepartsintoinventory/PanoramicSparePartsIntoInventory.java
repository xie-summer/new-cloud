package com.monitor.model.sparepartsintoinventory;

import java.util.Date;
import javax.persistence.*;

/**
 * 
 * @author gang
 *
 */
@Table(name = "panoramic_spare_parts_into_inventory")
public class PanoramicSparePartsIntoInventory {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 备件名
     */
    private String name;

    /**
     * 出入库时间
     */
    @Column(name = "in_out_time")
    private Date inOutTime;

    /**
     * 数量
     */
    private Double value;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private Double unitPrice;

    /**
     * 金额
     */
    @Column(name = "amount_price")
    private Double amountPrice;

    /**
     * 出入库类型
     */
    @Column(name = "in_out_type")
    private Integer inOutType;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 工厂id
     */
    @Column(name = "f_id")
    private Integer fId;

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
     * 获取备件名
     *
     * @return name - 备件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置备件名
     *
     * @param name 备件名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取出入库时间
     *
     * @return in_out_time - 出入库时间
     */
    public Date getInOutTime() {
        return inOutTime;
    }

    /**
     * 设置出入库时间
     *
     * @param inOutTime 出入库时间
     */
    public void setInOutTime(Date inOutTime) {
        this.inOutTime = inOutTime;
    }

    /**
     * 获取数量
     *
     * @return value - 数量
     */
    public Double getValue() {
        return value;
    }

    /**
     * 设置数量
     *
     * @param value 数量
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 获取单价
     *
     * @return unit_price - 单价
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置单价
     *
     * @param unitPrice 单价
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取金额
     *
     * @return amount_price - 金额
     */
    public Double getAmountPrice() {
        return amountPrice;
    }

    /**
     * 设置金额
     *
     * @param amountPrice 金额
     */
    public void setAmountPrice(Double amountPrice) {
        this.amountPrice = amountPrice;
    }

    /**
     * 获取出入库类型
     *
     * @return in_out_type - 出入库类型
     */
    public Integer getInOutType() {
        return inOutType;
    }

    /**
     * 设置出入库类型
     *
     * @param inOutType 出入库类型
     */
    public void setInOutType(Integer inOutType) {
        this.inOutType = inOutType;
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
     * 获取工厂id
     *
     * @return f_id - 工厂id
     */
    public Integer getfId() {
        return fId;
    }

    /**
     * 设置工厂id
     *
     * @param fId 工厂id
     */
    public void setfId(Integer fId) {
        this.fId = fId;
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
}