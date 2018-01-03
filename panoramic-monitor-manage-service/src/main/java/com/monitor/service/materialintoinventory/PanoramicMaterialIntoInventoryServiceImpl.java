package com.monitor.service.materialintoinventory;

import com.monitor.mapper.materialintoinventory.PanoramicMaterialIntoInventoryMapper;
import com.monitor.model.materialintoinventory.PanoramicMaterialIntoInventory;
import com.monitor.api.materialintoinventory.PanoramicMaterialIntoInventoryService;
import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 
 * @author gang
 *
 */
@Service("materialIntoInventoryService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicMaterialIntoInventoryServiceImpl extends AbstractService<PanoramicMaterialIntoInventory> implements PanoramicMaterialIntoInventoryService {
	@Autowired
    @Qualifier("materialIntoInventoryMapper")
    private PanoramicMaterialIntoInventoryMapper panoramicMaterialIntoInventoryMapper;

}
