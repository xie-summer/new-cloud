package com.cloud.user.dao.domain;

import com.cloud.model.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author summer
 * @since 2018-01-22
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "sys_dept_relation")
@Data
public class SysDeptRelation extends BaseObject {

  private static final long serialVersionUID = 1L;
  /** 祖先节点 */
  private Integer ancestor;
  /** 后代节点 */
  private Integer descendant;

  @Override
  public Serializable realId() {
    return null;
  }

  @Override
  public String toString() {
    return "SysDeptRelation{" + ", ancestor=" + ancestor + ", descendant=" + descendant + "}";
  }
}
