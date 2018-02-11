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
	 * @param code
	 * @param date
	 * @param code
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_auto),2) as valueAuto " + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	code = #{code}\n" + 
			"AND DATE_FORMAT(date , \'%Y-%m-%d\') BETWEEN DATE_ADD( " + 
			"	#{date} , " + 
			"	INTERVAL - DAY(#{date}) + 1 DAY " + 
			") " + 
			"AND #{date}")
	Double findThisMonthAutoSummary(@Param("code") String code,@Param("date") String date);

	/**
	 * 本月累计出库量
	 * @param code
	 * @param date
	 * @param code
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_manual),2) as valueManual " + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	code = #{code}\n" + 
			"AND DATE_FORMAT(date , \"%Y-%m-%d\") BETWEEN DATE_ADD( " + 
			"	#{date} , " + 
			"	INTERVAL - DAY(#{date}) + 1 DAY " + 
			") " + 
			"AND #{date}")
	Double findThisMonthManualSummary(@Param("code") String code,@Param("date") String date);

	/**
	 * 上月累计消耗量
	 * @param code
	 * @param date
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_auto),2) as valueAuto " + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	code = #{code}\n" + 
			"AND DATE_FORMAT(date , \"%Y-%m\") = date_format( " + 
			"	DATE_SUB(#{date} , INTERVAL 1 MONTH) , " + 
			"	\"%Y-%m\" " + 
			")")
	Double findLastMonthAutoSummary(@Param("code") String code,@Param("date") String date);
	
	/**
	 * 上月累计生产量
	 * @param code
	 * @param date
	 * @return
	 */
	@Select("SELECT " + 
			"	round(sum(value_manual),2) as valueManual " + 
			"FROM " + 
			"	panoramic_data_verification " + 
			"WHERE " + 
			"	code = #{code}\n" + 
			"AND DATE_FORMAT(date , \"%Y-%m\") = date_format( " + 
			"	DATE_SUB(#{date} , INTERVAL 1 MONTH) , " + 
			"	\"%Y-%m\" " + 
			")")
	Double findLastMonthManualSummary(@Param("code") String code, @Param("date") String date);
	
	/**
	 * 指定时间的计量，记录和偏差值
	 * @param code
	 * @param date
	 * @return
	 */
	@Select("SELECT\n" + 
			"	format(value_auto,2) as valueAuto,\n" + 
			"	format(value_manual,2) as valueManual,\n" + 
			"	format(bias*100,2) as bias \n" + 
			"FROM\n" + 
			"	panoramic_data_verification\n" + 
			"WHERE\n" + 
			"	code = #{code}\n" + 
			"AND DATE_FORMAT(date , \"%Y-%m-%d\") = #{date}")
	PanoramicDataVerificationDto findContentByDate(@Param("date") String date,@Param("code") String code);
}