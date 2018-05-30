package com.cloud.user.dao.domain;

import com.cloud.model.BaseObject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Table(name = "sys_role_menu")
public class SysRoleMenu extends BaseObject {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    /**
     * 菜单ID
     */
    private Integer menuId;

    @Override
    public Serializable realId() {
        return null;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }


    @Override
    public String toString() {
        return "SysRoleMenu{" +
                ", roleId=" + roleId +
                ", menuId=" + menuId +
                "}";
    }
}
