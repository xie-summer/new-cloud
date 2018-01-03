package com.monitor.web.support.token;

import com.monitor.model.acl.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author summer
 */
public class MemberEncodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -3802313724481182163L;
    private String memberEncode;
    private User member;

    public MemberEncodeAuthenticationToken(User member, String memberEncode) {
        super(member.getAuthorities());
        this.memberEncode = memberEncode;
        this.member = member;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return member;
    }

    @Override
    public void eraseCredentials() {
    }

    public String getMemberEncode() {
        return memberEncode;
    }

    public void setMemberEncode(String memberEncode) {
        this.memberEncode = memberEncode;
    }

    public User getMember() {
        return member;
    }
}
