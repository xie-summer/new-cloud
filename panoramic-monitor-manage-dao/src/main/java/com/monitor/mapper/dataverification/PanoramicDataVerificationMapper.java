package com.monitor.mapper.dataverification;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.cloud.core.Mapper;
import com.monitor.dto.dataverification.PanoramicDataVerificationDto;
import com.monitor.model.dataverification.PanoramicDataVerification;

/**
 * 
 * @author gang
 *
 */
@Repository("dataVerificationMapper")
public interface PanoramicDataVerificationMapper extends Mapper<PanoramicDataVerification> {
	
	/**
	 * 本月累计消耗量
	 * @param name
	 * @param date
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_auto),2) as valueAuto" + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	NAME = #{name}\n" + 
			"AND DATE_FORMAT(date , \"%Y-%m-%d\") BETWEEN DATE_ADD( " + 
			"	#{date} , " + 
			"	INTERVAL - DAY(#{date}) + 1 DAY " + 
			") " + 
			"AND #{date}")
	Double findThisMonthAutoSummary(@Param("name") String name,@Param("date") String date);

	/**
	 * 本月累计出库量
	 * @param name
	 * @param date
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_manual),2) as valueManual" + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	NAME = #{name}\n" + 
			"AND DATE_FORMAT(date , \"%Y-%m-%d\") BETWEEN DATE_ADD( " + 
			"	#{date} , " + 
			"	INTERVAL - DAY(#{date}) + 1 DAY " + 
			") " + 
			"AND #{date}")
	Double findThisMonthManualSummary(@Param("name") String name,@Param("date") String date);

	/**
	 * 上月累计消耗量
	 * @param name
	 * @param date
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_auto),2) as valueAuto" + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	NAME = #{name}\n" + 
			"AND DATE_FORMAT(date , '%Y-%m') = date_format( " + 
			"	DATE_SUB(#{date} , INTERVAL 1 MONTH) , " + 
			"	'%Y-%m' " + 
			")")
	Double findLastMonthAutoSummary(@Param("name") String name,@Param("date") String date);
	
	/**
	 * 上月累计生产量
	 * @param name
	 * @param date
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_manual),2) as valueManual" + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	NAME = #{name}\n" + 
			"AND DATE_FORMAT(date , '%Y-%m') = date_format( " + 
			"	DATE_SUB(#{date} , INTERVAL 1 MONTH) , " + 
			"	'%Y-%m' " + 
			")")
	Double findLastMonthManualSummary(@Param("name") String name, @Param("date") String date);
	
	/**
	 * 指定时间的计量，记录和偏差值
	 * @param name
	 * @param date
	 * @return
	 */
	@Select("SELECT\n" + 
			"	value_auto as valueAuto,\n" + 
			"	value_manual as valueManual,\n" + 
			"	bias\n" + 
			"FROM\n" + 
			"	panoramic_data_verification\n" + 
			"WHERE\n" + 
			"	NAME = #{name}\n" + 
			"AND DATE_FORMAT(date , '%Y-%m-%d') = #{date}")
	PanoramicDataVerificationDto findContentByDate(@Param("date") String date,@Param("name") String name);
}