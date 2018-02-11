package com.monitor.model.acl;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author summer
 */
public class User extends AbstractUser {
    private static final long serialVersionUID = 3832626162173359411L;
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String accountEnabled;
    private String citycode;
    private String mobile;
    private String rolenames;
    private String usertype;
    private String email;
    private List<GrantedAuthority> tmpAuth;

    public User() {
    }

    public User(String username) {
        this.username = StringUtils.lowerCase(username);
    }

    @Override
    public final List<GrantedAuthority> getAuthorities() {
        if (this.tmpAuth != null) {
            return this.tmpAuth;
        } else {
            this.tmpAuth = new ArrayList();
            if (StringUtils.isBlank(this.rolenames)) {
                return this.tmpAuth;
            } else {
                this.tmpAuth.addAll(AuthorityUtils.createAuthorityList(StringUtils.split(this.rolenames, ",")));
                return this.tmpAuth;
            }
        }
    }

    public void setAuthorities(List<GrantedAuthority> tmpAuth) {
        this.tmpAuth = tmpAuth;
    }

    @Override
    public final String getRolesString() {
        return this.rolenames;
    }

    @Override
    public final boolean isRole(String rolename) {
        String[] roles = StringUtils.split(this.rolenames, ",");
        return ArrayUtils.contains(roles, rolename);
    }

    @Override
    public String getRealname() {
        if (StringUtils.isBlank(this.username)) {
            return null;
        } else {
            int index = this.username.indexOf(64);
            return index > 0 ? "m-" + this.username.substring(0, index) : "m-" + this.username;
        }
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Serializable realId() {
        return this.id;
    }

    @Override
    public boolean isEnabled() {
        return "Y".equals(this.accountEnabled);
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCitycode() {
        return this.citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRolenames() {
        return this.rolenames;
    }

    public void setRolenames(String rolenames) {
        this.rolenames = rolenames;
    }

    @Override
    public String getUsertype() {
        return this.usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getAccountEnabled() {
        return this.accountEnabled;
    }

    public void setAccountEnabled(String accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
