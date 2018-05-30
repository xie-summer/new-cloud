package com.monitor.domain;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author lengleng
 * @since 2018-01-22
 */
@Table(name = "sys_dept")
public class SysDept extends BaseObject {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deptId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 是否删除  -1：已删除  0：正常
     */
    @Column(name = "del_flag")
    private String delFlag;
    @Column(name = "parent_id")
    private Integer parentId;

    @Override
    public Serializable realId() {
        return null;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysDept{" +
                ", deptId=" + deptId +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
