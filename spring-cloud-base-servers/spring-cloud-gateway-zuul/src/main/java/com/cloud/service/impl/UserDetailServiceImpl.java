package com.cloud.service.impl;

import com.cloud.common.vo.UserVo;
import com.cloud.feign.UserService;
import com.cloud.feign.UserServiceClient;
import com.cloud.util.UserDetailsImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author summer
 * @date 2017/10/26
 * <p>
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService, Serializable {
    @Autowired
    private UserServiceClient userService;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户不存在:" + username);
        }
        UserVo userVo = userService.findUserByUsername(username);
        return new UserDetailsImpl(userVo);
    }
}
