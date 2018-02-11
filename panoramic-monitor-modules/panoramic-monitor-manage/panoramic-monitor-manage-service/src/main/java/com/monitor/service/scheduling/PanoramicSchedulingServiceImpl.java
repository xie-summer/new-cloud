package com.monitor.service.scheduling;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.monitor.api.scheduling.PanoramicSchedulingService;
import com.monitor.mapper.scheduling.PanoramicSchedulingMapper;
import com.monitor.model.scheduling.PanoramicScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author summer
 * 2017/11/21.
 */
@Service("schedulingService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicSchedulingServiceImpl extends AbstractService<PanoramicScheduling> implements PanoramicSchedulingService {

    @Autowired
    @Qualifier("schedulingMapper")
    private PanoramicSchedulingMapper schedulingMapper;

}
