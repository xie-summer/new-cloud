package com.cloud.user.dao.model.user;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_member")
public class Member {
  @TableId(value = "memberId", type = IdType.AUTO)
  private Long memberId;

  private String username;

  private String password;

  private String salt;

  private String introduction;

  private String avatar;

  private Date createTime;

  private Date updateTime;

  private String delFlag;
}
