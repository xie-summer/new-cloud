package com.cloud.user.dao.domain.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author summer
 * @date 2017年11月9日23:33:27
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuTree extends TreeNode {
  private String icon;
  private String name;
  private String url;
  private boolean spread = false;
  private String path;
  private String component;
  private String authority;
  private String redirect;
  private String code;
  private String type;
  String label;

  public MenuTree() {
    super();
  }

  public MenuTree(int id, String name, int parentId) {
    this.id = id;
    this.parentId = parentId;
    this.name = name;
    this.label = name;
  }

  public MenuTree(int id, String name, MenuTree parent) {
    this.id = id;
    this.parentId = parent.getId();
    this.name = name;
    this.label = name;
  }
}
