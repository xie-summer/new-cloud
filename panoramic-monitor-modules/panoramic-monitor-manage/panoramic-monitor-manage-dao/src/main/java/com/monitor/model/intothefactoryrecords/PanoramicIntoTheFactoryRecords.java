package com.monitor.model.intothefactoryrecords;

import javax.persistence.*;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "panoramic_into_the_factory_records")
public class PanoramicIntoTheFactoryRecords {
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
     * 异常信息
     */
    @Column(name = "err_msg")
    private String errMsg;
    /**
     * 车牌
     */
    @Column(name = "number_plate")
    private String numberPlate;
    /**
     * 净重
     */
    @Column(name = "net_weight")
    private Double netWeight;
    /**
     * 扣重
     */
    private Double tare;
    /**
     * 单位
     */
    private String unit;
    /**
     * 进厂时间
     */
    @Column(name = "in_time")
    private Date inTime;
    /**
     * 出厂时间
     */
    @Column(name = "out_time")
    private Date outTime;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 删除标记（1：未删除；0：删除））
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;
    /**
     * 抓拍时间
     */
    @Column(name = "snapshot_time")
    private Date snapshotTime;
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
     * 备忘通知
     */
    private String memo;
    /**
     * 进出厂标志
     */
    @Column(name = "in_or_out")
    private Integer inOrOut;
    private String operator;

    /**
     * @return
     */
    public String getErrMsg() {
//        Double v = getNetWeight();
//        if (v == null || v == 0) {
//            return "数据异常,记录数据没有净重值或者净重为0";
//        }
//        if (getTare() / v >= 0.006) {
//            setErrMsg("超重");
//            return "超重";
//        }
//        if (getTare() / v <= -0.006) {
//            setErrMsg("缺重");
//            return "缺重";
//        }
        return errMsg;
    }

    /**
     * @param errMsg
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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
     * 获取车牌
     *
     * @return number_plate - 车牌
     */
    public String getNumberPlate() {
        return numberPlate;
    }

    /**
     * 设置车牌
     *
     * @param numberPlate 车牌
     */
    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    /**
     * 获取净重
     *
     * @return net_weight - 净重
     */
    public Double getNetWeight() {
        return netWeight;
    }

    /**
     * 设置净重
     *
     * @param netWeight 净重
     */
    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    /**
     * 获取扣重
     *
     * @return tare - 扣重
     */
    public Double getTare() {
        return tare;
    }

    /**
     * 设置扣重
     *
     * @param tare 扣重
     */
    public void setTare(Double tare) {
        this.tare = tare;
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
     * 获取进厂时间
     *
     * @return in_time - 进厂时间
     */
    public Date getInTime() {
        return inTime;
    }

    /**
     * 设置进厂时间
     *
     * @param inTime 进厂时间
     */
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    /**
     * 获取出厂时间
     *
     * @return out_time - 出厂时间
     */
    public Date getOutTime() {
        return outTime;
    }

    /**
     * 设置出厂时间
     *
     * @param outTime 出厂时间
     */
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取删除标记（1：未删除；0：删除））
     *
     * @return delete_flag - 删除标记（1：未删除；0：删除））
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除标记（1：未删除；0：删除））
     *
     * @param deleteFlag 删除标记（1：未删除；0：删除））
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 获取抓拍时间
     *
     * @return snapshot_time - 抓拍时间
     */
    public Date getSnapshotTime() {
        return snapshotTime;
    }

    /**
     * 设置抓拍时间
     *
     * @param snapshotTime 抓拍时间
     */
    public void setSnapshotTime(Date snapshotTime) {
        this.snapshotTime = snapshotTime;
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

    /**
     * 获取进出厂标志
     *
     * @return in_or_out - 进出厂标志
     */
    public Integer getInOrOut() {
        return inOrOut;
    }

    /**
     * 设置进出厂标志
     *
     * @param inOrOut 进出厂标志
     */
    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    /**
     * @return operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}