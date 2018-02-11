package com.itopener.zuul.route.spring.boot.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.itopener.zuul.route.spring.boot.common.rule.IZuulRouteRule;

public class ZuulRouteEntity implements Serializable {

	/** */
	private static final long serialVersionUID = -8909855285961467412L;

	/**
	 * The ID of the route (the same as its map key by default).
	 */
	private String id;

	/**
	 * The path (pattern) for the route, e.g. /foo/**.
	 */
	private String path;

	/**
	 * The service ID (if any) to map to this route. You can specify a
	 * physical URL or a service, but not both.
	 */
	private String serviceId;

	/**
	 * A full physical URL to map to the route. An alternative is to use a
	 * service ID and service discovery to find the physical address.
	 */
	private String url;

	/**
	 * Flag to determine whether the prefix for this route (the path, minus
	 * pattern patcher) should be stripped before forwarding.
	 */
	private boolean stripPrefix;

	/**
	 * Flag to indicate that this route should be retryable (if supported).
	 * Generally retry requires a service ID and ribbon.
	 */
	private boolean retryable;

	/**
	 * List of sensitive headers that are not passed to downstream requests.
	 * Defaults to a "safe" set of headers that commonly contain user
	 * credentials. It's OK to remove those from the list if the downstream
	 * service is part of the same system as the proxy, so they are sharing
	 * authentication data. If using a physical URL outside your own domain,
	 * then generally it would be a bad idea to leak user credentials.
	 */
	private Set<String> sensitiveHeadersSet = new LinkedHashSet<>();

	/** 字符串格式，与sensitiveHeaders对应，多个用逗号隔开 */
	private String sensitiveHeaders;

	private boolean customSensitiveHeaders;

	/** 是否可用 */
	private boolean enable;

	/** 路由器名称 */
	private String routerName;
	
	/** 路由规则*/
	private List<IZuulRouteRule> ruleList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isStripPrefix() {
		return stripPrefix;
	}

	public void setStripPrefix(boolean stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	public boolean isRetryable() {
		return retryable;
	}

	public void setRetryable(boolean retryable) {
		this.retryable = retryable;
	}

	public Set<String> getSensitiveHeadersSet() {
		return sensitiveHeadersSet;
	}

	public void setSensitiveHeadersSet(Set<String> sensitiveHeadersSet) {
		this.sensitiveHeadersSet = sensitiveHeadersSet;
		StringBuilder sb = new StringBuilder("");
		if (!CollectionUtils.isEmpty(sensitiveHeadersSet)) {
			for (String item : sensitiveHeadersSet) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(item);
			}
		}
		this.sensitiveHeaders = sb.toString();
	}

	public String getSensitiveHeaders() {
		return sensitiveHeaders;
	}

	public void setSensitiveHeaders(String sensitiveHeaders) {
		this.sensitiveHeaders = sensitiveHeaders;
		if (!StringUtils.isEmpty(sensitiveHeaders)) {
			this.sensitiveHeadersSet = new LinkedHashSet<>(Arrays.asList(sensitiveHeaders.split(",")));
		} else {
			this.sensitiveHeadersSet = new LinkedHashSet<String>();
		}
	}

	public boolean isCustomSensitiveHeaders() {
		return customSensitiveHeaders;
	}

	public void setCustomSensitiveHeaders(boolean customSensitiveHeaders) {
		this.customSensitiveHeaders = customSensitiveHeaders;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public List<IZuulRouteRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<IZuulRouteRule> ruleList) {
		this.ruleList = ruleList;
	}
	
}
