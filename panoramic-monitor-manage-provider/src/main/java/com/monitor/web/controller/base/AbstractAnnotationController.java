package com.monitor.web.controller.base;

import com.cloud.api.vo.ResultCode;
import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.model.acl.User;
import com.monitor.web.support.token.MemberEncodeAuthenticationToken;
import com.monitor.web.util.LoginUtils;
import com.monitor.web.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author summer
 */
public abstract class AbstractAnnotationController {
    protected static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(AbstractAnnotationController.class);

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes holder = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (holder != null) {
            HttpServletRequest request = holder.getRequest();
            if (request != null) {
                return request;
            }
        }
        return null;
    }

    protected HttpServletResponse getResponse() {
        ServletRequestAttributes holder = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (holder != null) {
            HttpServletResponse response = holder.getResponse();
            if (response != null) {
                return response;
            }
        }
        return null;
    }

    protected void download(String downloadType, HttpServletResponse response) {
        if (StringUtils.equals(downloadType, "xls")) {
            response.setContentType("application/xls;charset=UTF-8");
        } else if (StringUtils.equals(downloadType, "txt")) {
            response.setContentType("text/plain;charset=UTF-8");
        } else if (StringUtils.equals(downloadType, "jpg")) {
            response.setContentType("image/jpeg;charset=UTF-8");
        } else {
            response.setContentType("application/x-download");
        }
        response.addHeader("Content-Disposition", "attachment;filename=file" + DateUtil.format(new Date(), "yyMMdd_HHmmss") + "." + downloadType);
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    protected final User getLogonMember() {
        User member = getLogonMemberFromAuth();
        if (member == null) {
            HttpServletRequest request = getRequest();
            String ip = WebUtils.getRemoteIp(request);
            String sessid = LoginUtils.getSessid(request);
            // TODO 暂时没有做登录及权限校验  loginService.getLogonMemberByMemberEncodeAndIp(sessid, ip);
            ResultCode<MemberEncodeAuthenticationToken> result = null;
            if (result.isSuccess()) {
                member = result.getRetval().getMember();
            }
        }
        return member;
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    private final User getLogonMemberFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        // 登录
        if (auth.isAuthenticated() && auth instanceof MemberEncodeAuthenticationToken) {
            return ((MemberEncodeAuthenticationToken) auth).getMember();
        }
        return null;
    }

    /**
     * 设置rememberMe
     *
     * @param request
     * @param response
     * @param token
     */
    public void setLoginKey2Cookie(HttpServletRequest request, HttpServletResponse response, MemberEncodeAuthenticationToken token) {
        try {
            int duration = 60 * 60 * 3;
            if (StringUtils.isNotBlank(request.getParameter("rememberMe"))) {
                duration = 60 * 60 * 24 * 7;
            }
            setLogonSessid(token.getMemberEncode(), response, duration);
            LoginUtils.setLogonTrace(token.getMember().getId(), request, response);
        } catch (Exception e) {
            DB_LOGGER.warn(e, 20);
        }
    }

    /**
     * 设置sessid
     *
     * @param sessid
     * @param response
     * @param duration
     */
    private void setLogonSessid(String sessid, HttpServletResponse response, int duration) {
        Cookie cookie = new Cookie(LoginUtils.SESS_COOKIE_NAME, sessid);
        cookie.setMaxAge(duration);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
