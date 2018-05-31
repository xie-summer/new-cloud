package com.cloud.user.service.member;

import com.cloud.core.AbstractService;
import com.cloud.model.vo.UserVo;
import com.cloud.user.api.sys.SysUserService;
import com.cloud.user.dao.domain.SysUser;
import com.cloud.user.dao.domain.dto.sys.UserDto;
import com.cloud.user.dao.domain.dto.sys.UserInfo;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

/**
 * @author summer
 * 2018/5/31
 */
@Service
public class SysUserServiceImpl  extends AbstractService<SysUser> implements SysUserService {
    @Override
    public UserVo findUserByUsername(String username) {
        return null;
    }

    @Override
    public Page selectWithRolePage(String query) {
        return null;
    }

    @Override
    public UserInfo findUserInfo(UserVo userVo) {
        return null;
    }

    @Override
    public void saveImageCode(String randomStr, String imageCode) {

    }

    @Override
    public Boolean deleteUserById(SysUser sysUser) {
        return null;
    }

    @Override
    public Boolean updateUserInfo(UserDto userDto, String username) {
        return null;
    }

    @Override
    public Boolean updateUser(UserDto userDto, String username) {
        return null;
    }

    @Override
    public UserVo findUserByMobile(String mobile) {
        return null;
    }

    @Override
    public Boolean sendSmsCode(String mobile) {
        return null;
    }

    @Override
    public UserVo findUserByOpenId(String openId) {
        return null;
    }

    @Override
    public UserVo selectUserVoById(Integer id) {
        return null;
    }
}
