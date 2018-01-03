package com.monitor.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.GenericFilterBean;

import com.cloud.commons.api.ApiSysParamConstants;
import com.cloud.constant.api.ApiConstant;
import com.monitor.api.acl.ApiMobileService;
import com.monitor.model.api.ApiUser;
import com.monitor.model.member.Member;
import com.monitor.web.controller.api.OpenApiAuth;
import com.monitor.web.util.WebUtils;

//import com.cloud.util.WebUtils;

/**
 * API2.0身份过滤器
 *
 * @author summer
 */
public class OpenApiMobileAuthenticationFilter extends GenericFilterBean {
	private static ThreadLocal<OpenApiAuth> apiAuthLocal = new ThreadLocal<OpenApiAuth>();
	@Autowired
	@Qualifier("apiMobileService")
	private ApiMobileService apiMobileService;
	private String[] innerIpList;

	public static OpenApiAuth getOpenApiAuth() {
		return apiAuthLocal.get();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String callIp = WebUtils.getRemoteIp(request);
		if (!isInnerIp(callIp)) {
			response.sendError(403, "只能内部调用！");
			return;
		}
		String apptype = request.getParameter("apptype");
		if (StringUtils.isBlank(apptype)) {
			writeErrorResponse(response, ApiConstant.CODE_PARTNER_NOT_EXISTS, "apptype不能为空");
			return;
		}
		String appVersion = request.getParameter("appVersion");
		if (StringUtils.isBlank(appVersion)) {
			writeErrorResponse(response, ApiConstant.CODE_PARTNER_NOT_EXISTS, "appVersion不能为空");
			return;
		}
		String citycode = request.getParameter("citycode");
		String remoteIp = request.getHeader("GEWA-REMOTE-IP");

		if (StringUtils.isBlank(remoteIp)) {
			writeErrorResponse(response, ApiConstant.CODE_PARTNER_NORIGHTS, "非法调用！");
			return;
		}
		String appkey = request.getParameter(ApiSysParamConstants.APPKEY);
		ApiUser apiUser = apiMobileService.getApiUserByAppkey(appkey);
		if (apiUser == null) {
			writeErrorResponse(response, ApiConstant.CODE_PARTNER_NOT_EXISTS, "合作用户不存在");
			return;
		}
		String memberEncode = request.getParameter("memberEncode");
		Member member = null;
		// if(StringUtils.isNotBlank(memberEncode)){
		// member = memberService.getMemberByEncode(memberEncode);
		// if(member==null){
		// writeErrorResponse(response, ApiConstant.CODE_MEMBER_NOT_EXISTS,
		// "查无用户信息或登录信息过期,请重新登录重试！");
		// return;
		// }
		// response.addHeader(LoginUtils.COOKIE_NAME_TRACE,
		// LoginUtils.getTraceId(member.getId().toString()));
		// }
		try {
			// ApiUserExtra userExtra =
			// apiMobileService.getApiUserExtraById(apiUser.getId());
			apiAuthLocal.set(new OpenApiAuth(apiUser, remoteIp, member));
			chain.doFilter(request, response);
		} finally {
			apiAuthLocal.set(null);
			apiAuthLocal.remove();
		}
	}

	private boolean isInnerIp(String remoteIp) {
		for (String ipPre : innerIpList) {
			if (StringUtils.startsWith(remoteIp, ipPre)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void initFilterBean() throws ServletException {
		innerIpList = new String[] { "172.22.1.", "192.168.", "180.153.146.1", "114.80.171.2", "127.0.0.1" };
	}

	@Override
	public void destroy() {
	}

	private void writeErrorResponse(HttpServletResponse res, String code, String message) {
		res.setContentType("text/xml; charset=UTF-8");
		try {
			PrintWriter writer = res.getWriter();
			writer.write("<?xml  version=\"1.0\" encoding=\"UTF-8\" ?><data>");
			writer.write("<code>" + code + "</code>");
			writer.write("<error><![CDATA[" + message + "]]></error></data>");
		} catch (IOException e) {
		}
	}
}
