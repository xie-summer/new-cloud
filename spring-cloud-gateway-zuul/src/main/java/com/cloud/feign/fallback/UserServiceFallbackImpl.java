package com.cloud.feign.fallback;

import com.cloud.common.vo.UserVo;
import com.cloud.feign.UserService;
import com.cloud.feign.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author summer
 * @date 2017/10/31
 * 用户服务的fallback
 */
public class UserServiceFallbackImpl implements UserServiceClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserVo findUserByUsername(String username) {
        logger.error("调用{}异常:{}", "findUserByUsername", username);
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public UserVo find(Long id) {
        return null;
    }

    /**
     * @param ids
     * @return
     */
    @Override
    public List<UserVo> findAll(List<Long> ids) {
        return null;
    }
}
