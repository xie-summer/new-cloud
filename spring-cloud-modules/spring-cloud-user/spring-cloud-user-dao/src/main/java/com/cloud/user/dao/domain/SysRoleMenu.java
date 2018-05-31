package com.cloud.user.dao.domain;

import com.cloud.model.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色菜单表
 *
 * @author lengleng
 * @since 2017-10-29
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "sys_role_menu")
@Data
public class SysRoleMenu extends BaseObject {

  private static final long serialVersionUID = 1L;
  /** 角色ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer roleId;
  /** 菜单ID */
  private Integer menuId;

  @Override
  public Serializable realId() {
    return null;
  }

  @Override
  public String toString() {
    return "SysRoleMenu{" + ", roleId=" + roleId + ", menuId=" + menuId + "}";
  }
}
