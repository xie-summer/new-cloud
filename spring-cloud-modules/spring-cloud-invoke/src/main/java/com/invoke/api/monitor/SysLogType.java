package com.invoke.api.monitor;

/**
 * @author summer
 */
public enum SysLogType {
    /**
     * 异步任务队列
     */
    asynchTask("异步任务队列"),
    /**
     * 用户登录
     */
    userlogin("用户登录"),
    /**
     * Request请求统计日志
     */
    reqStats("Request请求统计日志"),
    /**
     * 短信发送失败
     */
    SMSERR("短信发送失败"),
    /**
     * 订单日志
     */
    order("订单日志"),
    /**
     * 命中座位缓存
     */
    hitCache("命中座位缓存"),
    /**
     * 使用电子券
     */
    useElecCard("使用电子券"),
    /**
     * 监控数据
     */
    monitor("监控数据"),
    /**
     * 报警短信
     */
    smswarn("报警短信"),
    /**
     * 终端监控日志
     */
    terminalLog("终端监控日志"),
    /**
     * openapi监控日志
     */
    openapiLog("openapi监控日志"),
    /**
     * 用户重要操作
     */
    userOp("用户重要操作"),
    /**
     * SA动作
     */
    saOp("SA动作");

    /**
     * 描述
     */
    private String description;

    /**
     * @param description
     */
    private SysLogType(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public String getDescription() {
        return this.description;
    }
}
