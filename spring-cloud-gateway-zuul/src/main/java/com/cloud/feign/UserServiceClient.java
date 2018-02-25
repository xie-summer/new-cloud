package com.cloud.feign;

import com.cloud.common.vo.UserVo;
import com.cloud.feign.fallback.UserServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author summer
 * @date 2017/10/31
 */
@FeignClient(name = "upms-service", fallback = UserServiceFallbackImpl.class)
@Service
public interface UserServiceClient extends UserService{
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/user/findUserByUsername/{username}")
    UserVo findUserByUsername(@PathVariable("username") String username);
}
