package com.cloud.user.dao.domain.dto.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author summer
 * @date 2018/1/20
 * 部门树
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeptTree extends TreeNode {
    private String name;

}
