package com.cloud.user.dao.domain.dto.user;

import lombok.Data;

/** @author summer */
@Data
public class PanoramicUserInfo {

  /** 登录名 */
  private String loginname;

  /** 头像 */
  private String headurl;

  /** 邮箱 */
  private String email;

  /** 密码 */
  private String password;
}
