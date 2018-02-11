package com.invoke.web.controller.api;


import com.invoke.model.api.ApiUser;

/**
 * @author sunmer
 * Api授权
 */
public class ApiAuth {
    private ApiUser apiUser;
    private String partnerIp;
    private String msg;
    private String code;
    private boolean checked;

    public ApiAuth(ApiUser apiUser) {
        this.apiUser = apiUser;
        this.msg = null;
    }

    public ApiAuth(ApiUser apiUser, String partnerIp, String msg, String code, boolean checked) {
        this.apiUser = apiUser;
        this.partnerIp = partnerIp;
        this.msg = msg;
        this.code = code;
        this.checked = checked;
    }

    public ApiAuth(ApiUser apiUser, String partnerIp) {
        this.apiUser = apiUser;
        this.msg = null;
        this.partnerIp = partnerIp;
    }

    public ApiUser getApiUser() {
        return apiUser;
    }

    public void setApiUser(ApiUser apiUser) {
        this.apiUser = apiUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isChecked() {
        return checked;
    }

    /**
     * 参数验证是否成功
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPartnerIp() {
        return partnerIp;
    }

    public void setPartnerIp(String partnerIp) {
        this.partnerIp = partnerIp;
    }

}
