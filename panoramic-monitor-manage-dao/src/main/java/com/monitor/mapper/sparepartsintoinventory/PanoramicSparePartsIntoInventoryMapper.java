package com.monitor.mapper.sparepartsintoinventory;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.cloud.core.Mapper;
import com.monitor.dto.sparepartsintoinventory.PanoramicSparePartsIntoInventoryDto;
import com.monitor.model.sparepartsintoinventory.PanoramicSparePartsIntoInventory;

/**
 * 
 * @author gang
 *
 */
@Repository("sparePartsIntoInventoryMapper")
public interface PanoramicSparePartsIntoInventoryMapper extends Mapper<PanoramicSparePartsIntoInventory> {
	
	/**
	 * 指定时间获取本周的备件出入库统计值内容
	 * @param date
	 * @param inOutType
	 * @return
	 */
	@Select("SELECT\n" + 
			"	name AS name ,\n" + 
			"	round(sum(amount_price),2) AS summary\n" + 
			"FROM\n" + 
			"	panoramic_spare_parts_into_inventory\n" + 
			"WHERE\n" + 
			"	DATE_FORMAT(in_out_time , \'%Y-%m-%d\') BETWEEN subdate(\n" + 
			"		#{date} ,\n" + 
			"		date_format(#{date} , '%w') - 1\n" + 
			"	)\n" + 
			"AND #{date}\n" + 
			"AND in_out_type IN(#{in_out_type})\n " + 
			"GROUP BY\n" + 
			"	name\n" + 
			"ORDER BY\n" + 
			"	summary DESC")
	List<PanoramicSparePartsIntoInventoryDto> findWeeklySummary(@Param("date") String date, @Param("in_out_type") String inOutType);
	
	/**
	 * 指定时间获取本月最大5件的备件出入库货值统计值
	 * @param date
	 * @return
	 */
	@Select("SELECT\n" + 
			"	name as name,\n" + 
			"	round(sum(case when in_out_type  = 0 then amount_price else 0 end),2) as summary\n" + 
			"FROM\n" + 
			"	panoramic_spare_parts_into_inventory\n" + 
			"WHERE\n" + 
			"	DATE_FORMAT(in_out_time , \'%Y-%m-%d\') BETWEEN DATE_ADD(\n" + 
			"		#{date} ,\n" + 
			"		INTERVAL - DAY(#{date}) + 1 DAY\n" + 
			"	)\n" + 
			"AND #{date}\n" + 
			"GROUP BY\n" + 
			"	name\n" + 
			"ORDER BY\n" + 
			"	summary DESC\n" + 
			"LIMIT 5")
	List<PanoramicSparePartsIntoInventoryDto> findMonthlyMaxPrice(@Param("date") String date);
	
	/**
	 * 指定时间获取本月最大5件的备件出入库量值统计值
	 * @param date
	 * @return
	 */
	@Select("SELECT\n" + 
			"	name as name,\n" + 
			"	round(sum(case when in_out_type  = 0 then value else 0 end),2) as summary\n" + 
			"FROM\n" + 
			"	panoramic_spare_parts_into_inventory\n" + 
			"WHERE\n" + 
			"	DATE_FORMAT(in_out_time , \'%Y-%m-%d\') BETWEEN DATE_ADD(\n" + 
			"		#{date} ,\n" + 
			"		INTERVAL - DAY(#{date}) + 1 DAY\n" + 
			"	)\n" + 
			"AND #{date}\n" + 
			"GROUP BY\n" + 
			"	name\n" + 
			"ORDER BY\n" + 
			"	summary DESC\n" + 
			"LIMIT 5")
	List<PanoramicSparePartsIntoInventoryDto> findMonthlyMaxValue(@Param("date") String date);
}