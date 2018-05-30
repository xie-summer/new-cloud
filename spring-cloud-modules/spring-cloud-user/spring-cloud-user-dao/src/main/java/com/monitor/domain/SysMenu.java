package com.monitor.domain;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author lengleng
 * @since 2017-11-08
 */
@Table(name = "sys_menu")
public class SysMenu extends BaseObject {

    private static final long serialVersionUID = 1L;
    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单权限标识
     */
    private String permission;
    /**
     * 请求链接
     */
    private String url;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 父菜单ID
     */
    @Column(name="parent_id")
    private Integer parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * VUE页面
     */
    private String component;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    private String type;
    /**
     * 创建时间
     */
    @Column(name="create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name="update_time")
    private Date updateTime;
    /**
     * 0--正常 1--删除
     */
    @Column(name="del_flag")
    private String delFlag;
    /**
     * 前端URL
     */
    private String path;

    @Override
    public Serializable realId() {
        return null;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        return "SysMenu{" +
                ", menuId=" + menuId +
                ", name=" + name +
                ", permission=" + permission +
                ", url=" + url +
                ", method=" + method +
                ", parentId=" + parentId +
                ", icon=" + icon +
                ", component=" + component +
                ", sort=" + sort +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
