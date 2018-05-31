package com.cloud.user.dao.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门管理
 *
 * @author summer
 * @since 2018-01-22
 */
@Table(name = "sys_dept")
@Data
public class SysDept {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer deptId;
  /** 部门名称 */
  private String name;
  /** 排序 */
  @Column(name = "order_num")
  private Integer orderNum;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
  /** 修改时间 */
  @Column(name = "update_time")
  private Date updateTime;
  /** 是否删除 -1：已删除 0：正常 */
  @Column(name = "del_flag")
  private String delFlag;

  @Column(name = "parent_id")
  private Integer parentId;

  @Override
  public String toString() {
    return "SysDept{"
        + ", deptId="
        + deptId
        + ", name="
        + name
        + ", orderNum="
        + orderNum
        + ", createTime="
        + createTime
        + ", updateTime="
        + updateTime
        + ", delFlag="
        + delFlag
        + "}";
  }
}
