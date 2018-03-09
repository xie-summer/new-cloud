package com.monitor.entity;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 角色与部门对应关系
 * </p>
 *
 * @author lengleng
 * @since 2018-01-20
 */
@Table(name = "sys_role_dept")
public class SysRoleDept extends BaseObject {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Integer roleId;
    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    private Integer deptId;

    @Override
    public Serializable realId() {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }


    @Override
    public String toString() {
        return "SysRoleDept{" +
                ", id=" + id +
                ", roleId=" + roleId +
                ", deptId=" + deptId +
                "}";
    }
}
