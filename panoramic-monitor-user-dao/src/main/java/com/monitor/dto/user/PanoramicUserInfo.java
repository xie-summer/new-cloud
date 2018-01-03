package com.monitor.dto.user;

/**
 * @author summer
 */
public class PanoramicUserInfo {

	/**
	 * 登录名
	 */
	private String loginname;
	
	/**
	 * 头像
	 */
	private String headurl;
	
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 密码
	 */
	private String password;
}
