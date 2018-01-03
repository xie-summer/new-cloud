package com.monitor.service.permission;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.monitor.mapper.permission.PanoramicPermissionMapper;
import com.monitor.model.permission.PanoramicPermission;
import com.panoramic.user.acl.PanoramicPermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 *@author summer
 * 2017/11/08.
 */
@Service("permissionService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicPermissionServiceImpl extends AbstractService<PanoramicPermission> implements PanoramicPermissionService {
	@Autowired
    @Qualifier("permissionMapper")
    private PanoramicPermissionMapper panoramicPermissionMapper;

}
