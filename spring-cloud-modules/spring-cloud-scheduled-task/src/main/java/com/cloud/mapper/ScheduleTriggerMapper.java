package com.cloud.mapper;

import com.cloud.core.Mapper;
import com.cloud.model.ScheduleTrigger;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("scheduleTriggerMapper")
public interface ScheduleTriggerMapper extends Mapper<ScheduleTrigger> {
}
