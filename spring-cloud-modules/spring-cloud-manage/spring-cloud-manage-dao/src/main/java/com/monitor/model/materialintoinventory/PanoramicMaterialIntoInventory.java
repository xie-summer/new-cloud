package com.monitor.model.materialintoinventory;

import java.util.Date;
import javax.persistence.*;

/**
 * 
 * @author gang
 *
 */
@Table(name = "panoramic_material_into_inventory")
public class PanoramicMaterialIntoInventory {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 物料名
     */
    private String name;

    /**
     * 物料编码
     */
    private String code;

    /**
     * 出入库时间
     */
    @Column(name = "in_out_time")
    private Date inOutTime;

    /**
     * 净重
     */
    private Double value;

    /**
     * 责任人
     */
    @Column(name = "person_liable")
    private String personLiable;

    /**
     * 责任人单位
     */
    @Column(name = "in_out_company")
    private String inOutCompany;

    /**
     * 物料类型1是原料0是产品
     */
    @Column(name = "mat_type")
    private Integer matType;

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
    private String fId;

    /**
     * 删除时间
     */
    private Date dtime;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date utime;

    /**
     * 抓取数据使用的标记
     */
    private String tag;

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
     * 获取净重
     *
     * @return value - 净重
     */
    public Double getValue() {
        return value;
    }

    /**
     * 设置净重
     *
     * @param value 净重
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 获取责任人
     *
     * @return person_liable - 责任人
     */
    public String getPersonLiable() {
        return personLiable;
    }

    /**
     * 设置责任人
     *
     * @param personLiable 责任人
     */
    public void setPersonLiable(String personLiable) {
        this.personLiable = personLiable;
    }

    /**
     * 获取责任人单位
     *
     * @return in_out_company - 责任人单位
     */
    public String getInOutCompany() {
        return inOutCompany;
    }

    /**
     * 设置责任人单位
     *
     * @param inOutCompany 责任人单位
     */
    public void setInOutCompany(String inOutCompany) {
        this.inOutCompany = inOutCompany;
    }

    /**
     * 获取物料类型1是原料0是产品
     *
     * @return mat_type - 物料类型1是原料0是产品
     */
    public Integer getMatType() {
        return matType;
    }

    /**
     * 设置物料类型1是原料0是产品
     *
     * @param matType 物料类型1是原料0是产品
     */
    public void setMatType(Integer matType) {
        this.matType = matType;
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
    public String getfId() {
        return fId;
    }

    /**
     * 设置工厂id
     *
     * @param fId 工厂id
     */
    public void setfId(String fId) {
        this.fId = fId;
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
     * 获取抓取数据使用的标记
     *
     * @return tag - 抓取数据使用的标记
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置抓取数据使用的标记
     *
     * @param tag 抓取数据使用的标记
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}