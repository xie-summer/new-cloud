package com.monitor.service.role;

import com.cloud.core.AbstractService;
import com.monitor.mapper.role.PanoramicRoleMapper;
import com.monitor.model.role.PanoramicRole;
import com.panoramic.user.acl.PanoramicRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author sunmer
 * on 2017/11/06.
 */
@Service("roleService")
@Transactional
public class PanoramicRoleServiceImpl extends AbstractService<PanoramicRole> implements PanoramicRoleService {
	@Autowired
    @Qualifier("roleMapper")
    private PanoramicRoleMapper panoramicRoleMapper;

}
