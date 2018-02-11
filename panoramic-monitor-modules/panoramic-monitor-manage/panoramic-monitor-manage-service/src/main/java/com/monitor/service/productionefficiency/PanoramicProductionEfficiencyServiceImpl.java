package com.monitor.service.productionefficiency;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.monitor.api.productionefficiency.PanoramicProductionEfficiencyService;
import com.monitor.mapper.productionefficiency.PanoramicProductionEfficiencyMapper;
import com.monitor.model.productionefficiency.PanoramicProductionEfficiency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author  summer
 * 2017/11/21
 */
@Service("productionEfficiencyService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicProductionEfficiencyServiceImpl extends AbstractService<PanoramicProductionEfficiency> implements PanoramicProductionEfficiencyService {
    @Autowired
    @Qualifier("productionEfficiencyMapper")
    private PanoramicProductionEfficiencyMapper productionEfficiencyMapper;

}
