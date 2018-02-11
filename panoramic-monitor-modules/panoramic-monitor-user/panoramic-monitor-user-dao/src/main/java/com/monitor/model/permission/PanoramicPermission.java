package com.monitor.model.permission;

import javax.persistence.*;

/**
 * @author summer
 */
@Table(name = "panoramic_permission")
public class PanoramicPermission {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * URL
     */
    private String url;

    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 菜单标题
     */
    @Column(name = "menu_title")
    private String menuTitle;

    /**
     * 角色名称
     */
    @Column(name = "role_names")
    private String roleNames;

    /**
     * 打开方式
     */
    private String target;

    /**
     * 是否显示
     */
    private String dispaly;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限名称
     *
     * @return name - 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名称
     *
     * @param name 权限名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取URL
     *
     * @return url - URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置URL
     *
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取菜单id
     *
     * @return menu_id - 菜单id
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单id
     *
     * @param menuId 菜单id
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取菜单标题
     *
     * @return menu_title - 菜单标题
     */
    public String getMenuTitle() {
        return menuTitle;
    }

    /**
     * 设置菜单标题
     *
     * @param menuTitle 菜单标题
     */
    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    /**
     * 获取角色名称
     *
     * @return role_names - 角色名称
     */
    public String getRoleNames() {
        return roleNames;
    }

    /**
     * 设置角色名称
     *
     * @param roleNames 角色名称
     */
    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    /**
     * 获取打开方式
     *
     * @return target - 打开方式
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置打开方式
     *
     * @param target 打开方式
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 获取是否显示
     *
     * @return dispaly - 是否显示
     */
    public String getDispaly() {
        return dispaly;
    }

    /**
     * 设置是否显示
     *
     * @param dispaly 是否显示
     */
    public void setDispaly(String dispaly) {
        this.dispaly = dispaly;
    }
}