package com.cloud.user.dao.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 字典表
 *
 * @author summer
 * @since 2017-11-19
 */
@Table(name = "sys_dict")
@Data
public class SysDict {

  private static final long serialVersionUID = 1L;
  /** 编号 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 数据值 */
  private String value;
  /** 标签名 */
  private String label;
  /** 类型 */
  private String type;
  /** 描述 */
  private String description;
  /** 排序（升序） */
  private BigDecimal sort;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
  /** 更新时间 */
  @Column(name = "update_time")
  private Date updateTime;
  /** 备注信息 */
  private String remarks;
  /** 删除标记 */
  @Column(name = "del_flag")
  private String delFlag;

  @Override
  public String toString() {
    return "SysDict{"
        + ", id="
        + id
        + ", value="
        + value
        + ", label="
        + label
        + ", type="
        + type
        + ", description="
        + description
        + ", sort="
        + sort
        + ", createTime="
        + createTime
        + ", updateTime="
        + updateTime
        + ", remarks="
        + remarks
        + ", delFlag="
        + delFlag
        + "}";
  }
}
