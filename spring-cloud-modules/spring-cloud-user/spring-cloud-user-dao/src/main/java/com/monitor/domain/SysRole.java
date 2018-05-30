package com.monitor.domain;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Table(name = "sys_role")
public class SysRole extends BaseObject {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_code")
    private String roleCode;
    @Column(name = "role_desc")
    private String roleDesc;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 删除标识（0-正常,1-删除）
     */
    @Column(name = "del_flag")
    private String delFlag;

    @Override
    public Serializable realId() {
        return null;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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


    @Override
    public String toString() {
        return "SysRole{" +
                ", roleId=" + roleId +
                ", roleName=" + roleName +
                ", roleCode=" + roleCode +
                ", roleDesc=" + roleDesc +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
