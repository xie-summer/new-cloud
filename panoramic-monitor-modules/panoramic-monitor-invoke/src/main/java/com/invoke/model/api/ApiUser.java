package com.invoke.model.api;

import com.cloud.model.BaseObject;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * @author summer
 */
public class ApiUser extends BaseObject {
    /**
     * 正常使用
     */
    public static final String STATUS_OPEN = "open";
    /**
     * 暂停使用
     */
    public static final String STATUS_PAUSE = "pause";
    /**
     * 停止使用
     */
    public static final String STATUS_STOP = "stop";
    private static final long serialVersionUID = -6800394265547863600L;
    /**
     *
     */
    private Long id;
    /**
     * 用户类型：partner
     */
    private String usertype;
    /**
     * 分类：ticket,sport....
     */
    private String category;
    /**
     * 合作伙伴名称
     */
    private String partnername;
    /**
     * 简称
     */
    private String briefname;
    /**
     * 合作伙伴IP
     */
    private String partnerip;
    /**
     * 验证身份密码
     */
    private String partnerkey;
    /**
     * 数据加密key
     */
    private String privatekey;
    /**
     * 描述
     */
    private String content;
    /**
     * 更新时间
     */
    private Timestamp updatetime;
    /**
     * 操作人
     */
    private Long clerk;
    /**
     * 当前状态：暂停使用、禁用、正常使用
     */
    private String status;
    /**
     * 分配的角色
     */
    private String roles;
    /**
     * 公司Path
     */
    private String partnerpath;
    /**
     * 3DES加密key Hex
     */
    private String secretKey;
    /**
     * json数据，其他信息
     */
    private String otherinfo;

    public ApiUser() {
    }

    public ApiUser(String partnerkey) {
        this.partnerkey = partnerkey;
        this.status = STATUS_PAUSE;
    }

    public final String getRolesString() {
        return roles;
    }

    public final boolean isRole(String rolename) {
        if (StringUtils.isBlank(roles)) {
            return false;
        }
        return Arrays.asList(roles.split(",")).contains(rolename);
    }

    public String getModifyLog(ApiUser old) {//只比较重要信息
        String diff = "";
        if (StringUtils.equals(partnerip, old.getPartnerip())) {
            diff += ",partnerip";
        }
        if (StringUtils.equals(partnerkey, old.getPartnerkey())) {
            diff += ",partnerkey";
        }
        if (StringUtils.equals(privatekey, old.getPrivatekey())) {
            diff += ",privatekey";
        }
        if (StringUtils.equals(status, old.getStatus())) {
            diff += ",status";
        }
        return diff;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getPartnerkey() {
        return partnerkey;
    }

    public void setPartnerkey(String partnerkey) {
        this.partnerkey = partnerkey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Serializable realId() {
        return id;
    }

    public String getPartnername() {
        return partnername;
    }

    public void setPartnername(String partnername) {
        this.partnername = partnername;
    }

    public String getPartnerip() {
        return partnerip;
    }

    public void setPartnerip(String partnerip) {
        this.partnerip = partnerip;
    }

    public Long getClerk() {
        return clerk;
    }

    public void setClerk(Long clerk) {
        this.clerk = clerk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public void copyFrom(ApiUser another) {
        partnername = another.partnername;
        partnerkey = another.partnerkey;
        privatekey = another.privatekey;
        partnerip = another.partnerip;
        status = another.status;
        briefname = another.briefname;
        content = another.content;
        roles = another.roles;
        secretKey = another.secretKey;
    }

    public boolean isEnabled() {
        return STATUS_OPEN.equals(status);
    }

    public boolean isValidIp(String remoteIp) {
        return StringUtils.isBlank(partnerip) || partnerip.contains(remoteIp);
    }

    public String getBriefname() {
        return briefname;
    }

    public void setBriefname(String briefname) {
        this.briefname = briefname;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPartnerpath() {
        return partnerpath;
    }

    public void setPartnerpath(String partnerpath) {
        this.partnerpath = partnerpath;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
