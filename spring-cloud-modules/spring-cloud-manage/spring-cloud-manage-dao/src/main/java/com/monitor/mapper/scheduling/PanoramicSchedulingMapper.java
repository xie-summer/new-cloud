package com.monitor.mapper.scheduling;

import com.cloud.core.IMapper;
import com.monitor.model.scheduling.PanoramicScheduling;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("schedulingMapper")
public interface PanoramicSchedulingMapper extends IMapper<PanoramicScheduling> {
}