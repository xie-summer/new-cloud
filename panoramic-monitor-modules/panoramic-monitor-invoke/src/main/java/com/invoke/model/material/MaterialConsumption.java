package com.invoke.model.material;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

/**
 * @author sunmer
 * 物料消耗DTO
 */
@Table(name = "hr_material_consumption_mid")
public class MaterialConsumption {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TAGNAME")
    private String tagname;

    @Column(name = "MAT_CODE")
    private String matCode;

    @Column(name = "MAT_NAME")
    private String matName;

    @Column(name = "CON_WEIGHT")
    private BigDecimal conWeight;

    @Column(name = "CON_UNIT")
    private String conUnit;

    @Column(name = "CON_TIME")
    private Date conTime;

    @Column(name = "SYS_TIME")
    private Date sysTime;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "DELETE_FLAG")
    private Integer deleteFlag;

    @Column(name = "RESERVED_INTEGER_1")
    private Integer reservedInteger1;

    @Column(name = "RESERVED_INTEGER_2")
    private Integer reservedInteger2;

    @Column(name = "RESERVED_DATE_1")
    private Date reservedDate1;

    @Column(name = "RESERVED_DATE_2")
    private Date reservedDate2;

    @Column(name = "OP_NAME")
    private String opName;

    @Column(name = "RESERVED_VARCHAR_2")
    private String reservedVarchar2;

    @Column(name = "RESERVED_VARCHAR_3")
    private String reservedVarchar3;

    /**
     * @return ID
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
     * @return TAGNAME
     */
    public String getTagname() {
        return tagname;
    }

    /**
     * @param tagname
     */
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    /**
     * @return MAT_CODE
     */
    public String getMatCode() {
        return matCode;
    }

    /**
     * @param matCode
     */
    public void setMatCode(String matCode) {
        this.matCode = matCode;
    }

    /**
     * @return MAT_NAME
     */
    public String getMatName() {
        return matName;
    }

    /**
     * @param matName
     */
    public void setMatName(String matName) {
        this.matName = matName;
    }

    /**
     * @return CON_WEIGHT
     */
    public BigDecimal getConWeight() {
        return conWeight;
    }

    /**
     * @param conWeight
     */
    public void setConWeight(BigDecimal conWeight) {
        this.conWeight = conWeight;
    }

    /**
     * @return CON_UNIT
     */
    public String getConUnit() {
        return conUnit;
    }

    /**
     * @param conUnit
     */
    public void setConUnit(String conUnit) {
        this.conUnit = conUnit;
    }

    /**
     * @return CON_TIME
     */
    public Date getConTime() {
        return conTime;
    }

    /**
     * @param conTime
     */
    public void setConTime(Date conTime) {
        this.conTime = conTime;
    }

    /**
     * @return SYS_TIME
     */
    public Date getSysTime() {
        return sysTime;
    }

    /**
     * @param sysTime
     */
    public void setSysTime(Date sysTime) {
        this.sysTime = sysTime;
    }

    /**
     * @return NOTES
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return DELETE_FLAG
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return RESERVED_INTEGER_1
     */
    public Integer getReservedInteger1() {
        return reservedInteger1;
    }

    /**
     * @param reservedInteger1
     */
    public void setReservedInteger1(Integer reservedInteger1) {
        this.reservedInteger1 = reservedInteger1;
    }

    /**
     * @return RESERVED_INTEGER_2
     */
    public Integer getReservedInteger2() {
        return reservedInteger2;
    }

    /**
     * @param reservedInteger2
     */
    public void setReservedInteger2(Integer reservedInteger2) {
        this.reservedInteger2 = reservedInteger2;
    }

    /**
     * @return RESERVED_DATE_1
     */
    public Date getReservedDate1() {
        return reservedDate1;
    }

    /**
     * @param reservedDate1
     */
    public void setReservedDate1(Date reservedDate1) {
        this.reservedDate1 = reservedDate1;
    }

    /**
     * @return RESERVED_DATE_2
     */
    public Date getReservedDate2() {
        return reservedDate2;
    }

    /**
     * @param reservedDate2
     */
    public void setReservedDate2(Date reservedDate2) {
        this.reservedDate2 = reservedDate2;
    }

    /**
     * @return OP_NAME
     */
    public String getOpName() {
        return opName;
    }

    /**
     * @param opName
     */
    public void setOpName(String opName) {
        this.opName = opName;
    }

    /**
     * @return RESERVED_VARCHAR_2
     */
    public String getReservedVarchar2() {
        return reservedVarchar2;
    }

    /**
     * @param reservedVarchar2
     */
    public void setReservedVarchar2(String reservedVarchar2) {
        this.reservedVarchar2 = reservedVarchar2;
    }

    /**
     * @return RESERVED_VARCHAR_3
     */
    public String getReservedVarchar3() {
        return reservedVarchar3;
    }

    /**
     * @param reservedVarchar3
     */
    public void setReservedVarchar3(String reservedVarchar3) {
        this.reservedVarchar3 = reservedVarchar3;
    }

}