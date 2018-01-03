package com.monitor.mapper.productofflinemeasurement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.cloud.core.Mapper;
import com.monitor.dto.productmaterials.PanoramicProductMaterialsDto;
import com.monitor.dto.realtimeconsumption.PanoramicRealTimeConsumptionDto;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;

/**
 * 
 * @author gang
 *
 */
@Repository("productOfflineMeasurementMapper")
public interface PanoramicProductOfflineMeasurementMapper extends Mapper<PanoramicProductOfflineMeasurement> {
	
    /**
     * 查询当天早中晚班中的分时数据
     * @param date
     * @param code
     * @return
     */
    @Select("SELECT\n" + 
    		"	DATE_FORMAT(ctime , \"%H\") AS HOUR ,\n" + 
    		"	round(sum(VALUE) , 2) AS\n" + 
    		"VALUE\n" + 
    		"\n" + 
    		"FROM\n" + 
    		"	panoramic_product_offline_measurement\n" + 
    		"WHERE\n" + 
    		"	CODE = #{code} \n" + 
    		"AND DATE_FORMAT(ctime , \"%Y-%m-%d\") = #{date}\n" + 
    		"AND DATE_FORMAT(ctime , \"%H\") BETWEEN 00 AND 24\n" + 
    		"GROUP BY\n" + 
    		"	DATE_FORMAT(ctime , \"%Y%m%d%H\")")
    List<PanoramicProductMaterialsDto> listTimelyByDate(
    		@Param("date") String date,
    		@Param("code") String code);
    
    /**
     * 查询当天早中晚班各自合计数据
     * @param code
     * @param date
     * @param startHour
     * @param endHour
     * @return
     * 
     */
    @Select("SELECT\n" + 
    		"	round(sum(VALUE) , 2) AS VALUE\n" + 
    		"FROM\n" + 
    		"	panoramic_product_offline_measurement\n" + 
    		"WHERE\n" + 
    		"	CODE = #{code} \n" + 
    		"AND DATE_FORMAT(ctime , \"%Y-%m-%d\") = #{date}\n" + 
    		"AND DATE_FORMAT(ctime , \"%H\") BETWEEN #{startHour} and #{endHour}")
    PanoramicProductMaterialsDto listTimelyAmountByDate(
    		@Param("code") String code,
    		@Param("date") String date,
    		@Param("startHour") String startHour,
    		@Param("endHour") String endHour);    
}