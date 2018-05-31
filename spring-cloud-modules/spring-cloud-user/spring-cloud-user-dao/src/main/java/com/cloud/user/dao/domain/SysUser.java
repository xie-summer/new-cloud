package com.cloud.user.dao.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Table(name = "sys_user")
@Data
public class SysUser {

  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;
  /** 用户名 */
  private String username;

  private String password;
  /** 随机盐 */
  private String salt;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
  /** 修改时间 */
  @Column(name = "update_time")
  private Date updateTime;
  /** 0-正常，1-删除 */
  @Column(name = "del_flag")
  private String delFlag;

  /** 简介 */
  private String introduction;
  /** 头像 */
  private String avatar;

  /** 部门ID */
  @Column(name = "dept_id")
  private Integer deptId;

  @Override
  public String toString() {
    return "SysUser{"
        + "userId="
        + userId
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + ", salt='"
        + salt
        + '\''
        + ", createTime="
        + createTime
        + ", updateTime="
        + updateTime
        + ", delFlag='"
        + delFlag
        + '\''
        + ", introduction='"
        + introduction
        + '\''
        + ", avatar='"
        + avatar
        + '\''
        + ", deptId="
        + deptId
        + '}';
  }
}
