package com.cloud.user.dao.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色表
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Table(name = "sys_user_role")
@Data
public class SysUserRole {

  private static final long serialVersionUID = 1L;

  /** 用户ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;
  /** 角色ID */
  private Integer roleId;

  @Override
  public String toString() {
    return "SysUserRole{" + ", userId=" + userId + ", roleId=" + roleId + "}";
  }
}
