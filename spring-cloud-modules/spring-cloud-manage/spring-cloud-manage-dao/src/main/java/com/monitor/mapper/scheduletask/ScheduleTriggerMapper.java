package com.monitor.mapper.scheduletask;

import com.cloud.core.IMapper;
import com.monitor.model.task.ScheduleTrigger;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("scheduleTriggerMapper")
public interface ScheduleTriggerMapper extends IMapper<ScheduleTrigger> {
}
