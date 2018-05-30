package com.monitor.domain;

import com.cloud.model.BaseObject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Table(name="sys_user_role")
public class SysUserRole extends BaseObject {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
    /**
     * 角色ID
     */
	private Integer roleId;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	@Override
	public String toString() {
		return "SysUserRole{" +
			", userId=" + userId +
			", roleId=" + roleId +
			"}";
	}

	@Override
	public Serializable realId() {
		return null;
	}
}
