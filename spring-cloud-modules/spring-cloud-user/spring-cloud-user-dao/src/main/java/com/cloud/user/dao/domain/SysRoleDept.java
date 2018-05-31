package com.cloud.user.dao.domain;

import com.cloud.model.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色与部门对应关系
 *
 * @author summer
 * @since 2018-01-20
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "sys_role_dept")
@Data
public class SysRoleDept extends BaseObject {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 角色ID */
  @Column(name = "role_id")
  private Integer roleId;
  /** 部门ID */
  @Column(name = "dept_id")
  private Integer deptId;

  @Override
  public Serializable realId() {
    return null;
  }

  @Override
  public String toString() {
    return "SysRoleDept{" + ", id=" + id + ", roleId=" + roleId + ", deptId=" + deptId + "}";
  }
}
