package com.monitor.service.materialintoinventory;

import com.monitor.mapper.materialintoinventory.PanoramicMaterialIntoInventoryMapper;
import com.monitor.model.materialintoinventory.PanoramicMaterialIntoInventory;
import com.monitor.api.materialintoinventory.PanoramicMaterialIntoInventoryService;
import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextListener;



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

	@Override
	public List<PanoramicMaterialIntoInventory> findMaterialValue(String code, String type, String date) {
		
		List<PanoramicMaterialIntoInventory> result = 
				panoramicMaterialIntoInventoryMapper.findMaterialValue(code,type,date) ;
		
		return result;
	}

	@Override
	public PanoramicMaterialIntoInventory findSummaryByDate(String code,String type,String date) {
		PanoramicMaterialIntoInventory reult = panoramicMaterialIntoInventoryMapper.findSummaryByDate(code,type,date);
		return reult;
	}

}
