package com.cloud.support;

/**
 * @author summer
 */
public enum ERROR_CODE {
    /**
     * 成功
     */
    SUCCESS("200"),
    /**
     * 失败
     */
    FAIL("400"),
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED("401"),
    /**
     * 接口不存在
     */
    NOT_FOUND("404"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR("500");

    public String code;

    ERROR_CODE(String code) {
        this.code = code;
    }
}
