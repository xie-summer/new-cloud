package com.monitor.api.sparepartsintoinventory;

import com.monitor.dto.sparepartsintoinventory.PanoramicSparePartsIntoInventoryDto;
import com.monitor.model.sparepartsintoinventory.PanoramicSparePartsIntoInventory;

import java.util.List;

import com.cloud.core.Service;


/**
* @author xuegang
* 2017/12/26.
*/
public interface PanoramicSparePartsIntoInventoryService extends Service<PanoramicSparePartsIntoInventory> {
	
	/**
	 * 周单位获取入库产品货值
	 * @param date
	 * @return
	 */
	List<PanoramicSparePartsIntoInventoryDto> findWeeklyInSummary(String date);
	
	/**
	 * 周单位获取出库产品货值
	 * @param date
	 * @return
	 * 
	 */
	List<PanoramicSparePartsIntoInventoryDto> findWeeklyOutSummary(String date);
	
	/**
	 * 月单位获取消耗产品货值
	 * @param date
	 * @return
	 */
	List<PanoramicSparePartsIntoInventoryDto> findMonthlyMaxPrice(String date);
	
	/**
	 * 月单位获取消耗产品量
	 * @param date
	 * @return
	 */
	List<PanoramicSparePartsIntoInventoryDto> findMonthlyMaxValue(String date);
	
}
