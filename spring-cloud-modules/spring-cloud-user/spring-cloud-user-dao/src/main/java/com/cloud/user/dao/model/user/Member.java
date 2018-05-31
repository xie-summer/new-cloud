package com.cloud.user.dao.model.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/** @author summer */
@Data
@Table(name = "member")
public class Member {
  /** ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 登录名 */
  @Column(name = "login_name")
  private String loginName;

  /** 密码 */
  private String password;

  /** 性别 */
  private String sex;

  /** 头像 */
  @Column(name = "head_url")
  private String headUrl;

  /** 生日 */
  private Date birthday;

  /** 手机号码 */
  private String mobile;

  /** 邮箱 */
  private String email;

  /** 是否可用（1：可用；0：不可用） */
  private Integer status;

  /** 创建时间 */
  private Date ctime;

  /** 更新时间 */
  private Date utime;

  /** 删除时间 */
  private Date dtime;

  /** 操作人 */
  private String operator;


}
