package com.monitor.model.acl;

import com.cloud.model.BaseObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author summer
 */
public abstract class AbstractUser extends BaseObject implements UserDetails {
    private static final long serialVersionUID = 6078080839080249253L;

    public AbstractUser() {
    }

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

    public abstract boolean isRole(String var1);

    public abstract String getRolesString();

    public abstract String getRealname();

    public abstract Long getId();

    public abstract String getUsertype();

}
