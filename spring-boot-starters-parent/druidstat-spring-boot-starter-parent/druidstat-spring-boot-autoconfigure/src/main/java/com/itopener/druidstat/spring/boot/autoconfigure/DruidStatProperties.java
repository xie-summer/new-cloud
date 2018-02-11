package com.itopener.druidstat.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**  
 * @author fuwei.deng
 * @Date 2017年6月9日 下午3:10:58
 * @version 1.0.0
 */
@ConfigurationProperties(prefix="spring.druid.stat")
public class DruidStatProperties {

	private String url = "/druid/*";
	
	private String allow;
	
	private String deny;
	
	private String username;
	
	private String password;
	
	private String reset = "true";
	
	private boolean profileEnable = true;
	
	private String exclusions = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*";
	
	private String filterName = "druidWebStatFilter";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getDeny() {
		return deny;
	}

	public void setDeny(String deny) {
		this.deny = deny;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReset() {
		return reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public boolean isProfileEnable() {
		return profileEnable;
	}

	public void setProfileEnable(boolean profileEnable) {
		this.profileEnable = profileEnable;
	}

	public String getExclusions() {
		return exclusions;
	}

	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

}
