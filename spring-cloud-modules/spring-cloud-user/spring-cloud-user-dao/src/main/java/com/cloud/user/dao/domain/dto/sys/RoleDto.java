package com.cloud.user.dao.domain.dto.sys;


import com.cloud.user.dao.domain.SysRole;
import lombok.Data;

/**
 * @author summer
 * @date 2018/1/20
 * 角色Dto
 */
@Data
public class RoleDto extends SysRole {
    /**
     * 角色部门Id
     */
    private Integer roleDeptId;

    /**
     * 部门名称
     */
    private String deptName;

}
