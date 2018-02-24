package com.framework.auth.controller;

import com.auth.common.util.R;
import com.framework.auth.component.social.SocialUserInfo;
import com.framework.auth.component.social.qq.connect.QQOAuth2Template;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author summer
 * @date 2018/1/21
 * 控制社交登录
 */
@Controller
public class SocialController {
    private final Logger log = LoggerFactory.getLogger(SocialController.class);
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 未注册用户选择三方登录 重定向
     *
     * @param request  request
     * @return SocialUserInfo
     */
    @RequestMapping("/signup")
    @ResponseBody
    public R<SocialUserInfo> signUp(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadImg(connection.getImageUrl());
        return new R<>(userInfo);
    }

    @RequestMapping("/binding")
    public R<Boolean> bindingSocial(Authentication authentication, HttpServletRequest request) {
        String username = (String) authentication.getPrincipal();
        providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
        return new R<>(Boolean.TRUE);
    }
}
