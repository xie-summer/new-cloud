package com.monitor.model.member;

import com.cloud.model.acl.BaseUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author summer
 */
public class Member extends BaseUser {
    private static final long serialVersionUID = -5010141453720441090L;
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String mobile;
    private String rejected;
    /**
     * //N：未绑定，X：未知，特殊用途，Y:绑定，Y_S：手机能通话验证过
     */
    private String bindStatus;
    /**
     * //分配的角色
     */
    private String roles = "member";
    private List<GrantedAuthority> tmpAuth;

    public Member() {
    }

    public Member(String nickname) {
        this.rejected = "N";
        this.nickname = nickname;
        this.bindStatus = "N";
    }

    @Override
    public final List<GrantedAuthority> getAuthorities() {
        if (tmpAuth != null) {
            return tmpAuth;
        }
        tmpAuth = new ArrayList<GrantedAuthority>();
        tmpAuth.addAll(AuthorityUtils.createAuthorityList(roles.split(",")));
        return tmpAuth;
    }

    @Override
    public final String getRolesString() {
        return "member";
    }

    @Override
    public final boolean isRole(String rolename) {
        return Arrays.asList(roles.split(",")).contains(rolename);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    @Override
    public boolean isEnabled() {
        return "N".equals(rejected);
    }

    @Override
    public Serializable realId() {
        return id;
    }

    @Override
    public String getRealname() {
        return this.nickname;
    }

    @Override
    public String getUsername() {
        return StringUtils.isBlank(email) ? mobile : email;
    }

    public String getSmobile() {
        if (StringUtils.isNotBlank(mobile) && mobile.length() == 11) {
            return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
        }
        return "";
    }

    @Override
    public String getUsertype() {
        return "member";
    }

    public boolean isBindMobile() {
        return StringUtils.isNotBlank(mobile) && StringUtils.startsWith(bindStatus, "Y");
    }
}
