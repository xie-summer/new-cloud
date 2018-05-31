package com.cloud.zuul.constant;

/**
 * @author summer
 * @date 2018/1/25
 * 服务名称
 */
public interface ServiceNameConstant {
    /**
     * 认证服务的SERVICEID（zuul 配置的对应）
     */
    String AUTH_SERVICE = "cloud-auth-service";

    /**
     * UMPS模块
     */
    String UMPS_SERVICE = "upms-service";
    /**
     * 后台模块
     */
    String MONITOR_MANAGE_SERVICE = "monitor-manage-service";
    /**
     * USER模块
     */
    String USERS_SERVICE = "users-service";
}
