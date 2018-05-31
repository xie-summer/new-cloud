package com.cloud.user.dao.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色菜单表
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Table(name = "sys_role_menu")
@Data
public class SysRoleMenu {

  private static final long serialVersionUID = 1L;
  /** 角色ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer roleId;
  /** 菜单ID */
  private Integer menuId;

  @Override
  public String toString() {
    return "SysRoleMenu{" + ", roleId=" + roleId + ", menuId=" + menuId + "}";
  }
}
