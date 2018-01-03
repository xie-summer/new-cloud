package com.cloud.model.acl;


import com.cloud.model.BaseObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author summer
 */
public abstract class BaseUser extends BaseObject implements UserDetails {
    public static final String USER_TYPE_MEMBER = "user_type_member";
    private static final long serialVersionUID = 6078080839080249253L;

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public abstract List<GrantedAuthority> getAuthorities();

    public abstract boolean isRole(String rolename);

    public abstract String getRolesString();

    public abstract String getRealname();

    public abstract Long getId();

    public abstract String getUsertype();
}
