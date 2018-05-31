package com.cloud.user.dao.domain.dto.sys;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author summer
 * @date 2017年11月9日23:33:45
 */
@Data
public class TreeNode {
  public int id;
  public int parentId;
  public List<TreeNode> children = Lists.newArrayList();

  public void add(TreeNode node) {
    children.add(node);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }
}
