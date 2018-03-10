package com.monitor.mapper.systemconfiguration;

import com.cloud.core.IMapper;
import com.monitor.model.systemconfiguration.PanoramicSystemConfiguration;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("systemConfigurationMapper")
public interface PanoramicSystemConfigurationMapper extends IMapper<PanoramicSystemConfiguration> {
}