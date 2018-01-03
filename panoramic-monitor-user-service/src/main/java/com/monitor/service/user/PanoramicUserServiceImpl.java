package com.monitor.service.user;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.monitor.dto.user.PanoramicUserInfo;
import com.monitor.mapper.user.PanoramicUserMapper;
import com.monitor.model.user.PanoramicUser;
import com.panoramic.user.acl.PanoramicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Created by @author xuegang on 2017/12/20.
 */
@Service("userService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicUserServiceImpl extends AbstractService<PanoramicUser> implements PanoramicUserService {

    @Autowired
    @Qualifier("userMapper")
    private PanoramicUserMapper panoramicUserMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public PanoramicUserInfo getUserInfo(String userName, String password) {
        //用户数据查询
        PanoramicUserInfo result = panoramicUserMapper.findByUser(userName);

        if (null == result) {
            return null;
        }
        if (!Objects.equals(password, result.getPassword())) {
            return null;
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public PanoramicUserInfo loginWeb(String userName, String password) {

        PanoramicUserInfo result = getUserInfo(userName, password);

        if (null != result) {
            return result;
        }
        return null;
    }

    @Override
    public PanoramicUserInfo logoutWeb(String userName) {
        return null;
    }
}
