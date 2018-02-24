package com.framework.auth.config;

import lombok.Data;

/**
 * @author summer
 * @date 2017/10/28
 * social 登录基础配置
 */
public class SocialPropertiesConfig {
    /**
     * 提供商
     */
    private String providerId;
    /**
     * 应用ID
     */
    private String clientId;
    /**
     * 应用密钥
     */
    private String clientSecret;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
