package com.cloud.user.dao.domain.dto.sys;

import com.cloud.user.dao.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author summer
 * @date 2017/11/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends SysUser {
  /** 角色ID */
  private Integer role;

  private Integer deptId;

  /** 新密码 */
  private String newpassword1;
}
