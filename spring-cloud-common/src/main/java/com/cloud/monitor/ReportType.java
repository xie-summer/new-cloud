package com.cloud.monitor;

/**
 * @author gebiao(ge.biao@gewara.com)
 * @since Mar 3, 2014 11:57:56 AM
 */
public enum ReportType {
	/**
	 *
	 */
	reqnum("正在处理的请求数量"),
	/**
	 *
	 */
	dubbonum("DUBBO正在处理的请求数量"),
	/**
	 *
	 */
	jmsnum("正在发送的JMS消息数量"),
	/**
	 *
	 */
	jvmdump("thread dump"),
	/**
	 *
	 */
	reqstats("请求统计"),
	/**
	 *
	 */
	exception("异常统计"),
	/**
	 *
	 */
	scache("ServiceCache"),
	/**
	 *
	 */
	pcache("PageCache"),
	/**
	 *
	 */
	mongo("mongo表统计"),
	/**
	 *
	 */
	gcache("GuavaCache"),
	/**
	 *
	 */
	dubboReq("dubbo接口请求"),
	/**
	 *
	 */
	gworder("gworder请求处理"),
	/**
	 * //dubbo.xxxx, exception.xxxx
	 */
	error("各类错误");
	
	private String description;
	public String getDescription() {
		return description;
	}
	private ReportType(String description){
		this.description = description;
	}
	
}
