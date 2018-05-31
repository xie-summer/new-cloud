package com.cloud.user.dao.domain;

import com.cloud.model.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单权限表
 *
 * @author summer
 * @since 2017-11-08
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "sys_menu")
@Data
public class SysMenu extends BaseObject {

  private static final long serialVersionUID = 1L;
  /** 菜单ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer menuId;
  /** 菜单名称 */
  private String name;
  /** 菜单权限标识 */
  private String permission;
  /** 请求链接 */
  private String url;
  /** 请求方法 */
  private String method;
  /** 父菜单ID */
  @Column(name = "parent_id")
  private Integer parentId;
  /** 图标 */
  private String icon;
  /** VUE页面 */
  private String component;
  /** 排序值 */
  private Integer sort;
  /** 菜单类型 （0菜单 1按钮） */
  private String type;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
  /** 更新时间 */
  @Column(name = "update_time")
  private Date updateTime;
  /** 0--正常 1--删除 */
  @Column(name = "del_flag")
  private String delFlag;
  /** 前端URL */
  private String path;

  @Override
  public Serializable realId() {
    return null;
  }

  @Override
  public String toString() {
    return "SysMenu{"
        + ", menuId="
        + menuId
        + ", name="
        + name
        + ", permission="
        + permission
        + ", url="
        + url
        + ", method="
        + method
        + ", parentId="
        + parentId
        + ", icon="
        + icon
        + ", component="
        + component
        + ", sort="
        + sort
        + ", type="
        + type
        + ", createTime="
        + createTime
        + ", updateTime="
        + updateTime
        + ", delFlag="
        + delFlag
        + "}";
  }
}
