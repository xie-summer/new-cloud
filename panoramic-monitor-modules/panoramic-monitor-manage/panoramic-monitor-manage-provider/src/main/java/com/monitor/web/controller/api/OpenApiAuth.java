package com.monitor.web.controller.api;


import com.monitor.model.api.ApiUser;
import com.monitor.model.member.Member;

/**
 * @author summer
 */
public class OpenApiAuth extends ApiAuth {
    private String remoteIp;
    private Member member;

    public OpenApiAuth(ApiUser apiUser, String remoteIp, Member member) {
        super(apiUser);
        this.remoteIp = remoteIp;
        this.member = member;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public Member getMember() {
        return member;
    }
}
