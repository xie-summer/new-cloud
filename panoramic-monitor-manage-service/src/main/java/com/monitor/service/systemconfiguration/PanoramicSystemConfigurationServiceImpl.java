package com.monitor.service.systemconfiguration;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.monitor.api.systemconfiguration.PanoramicSystemConfigurationService;
import com.monitor.mapper.systemconfiguration.PanoramicSystemConfigurationMapper;
import com.monitor.model.systemconfiguration.PanoramicSystemConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author sunmer
 * 2017/11/21.
 */
@Service("systemConfigurationService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicSystemConfigurationServiceImpl extends AbstractService<PanoramicSystemConfiguration> implements PanoramicSystemConfigurationService {
    @Autowired
    @Qualifier("systemConfigurationMapper")
    private PanoramicSystemConfigurationMapper systemConfigurationMapper;

}
