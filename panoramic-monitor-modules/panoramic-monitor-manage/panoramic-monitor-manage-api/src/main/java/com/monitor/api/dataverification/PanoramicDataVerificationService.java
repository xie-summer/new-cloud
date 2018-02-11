package com.monitor.api.dataverification;
import com.monitor.dto.dataverification.PanoramicDataVerificationDto;
import com.monitor.model.dataverification.PanoramicDataVerification;
import com.cloud.core.Service;


/**
* 数据校验查询
* @author xuegang
* 2017/12/27.
*/
public interface PanoramicDataVerificationService extends Service<PanoramicDataVerification> {

	/**
	 * 指定时间查询本月度偏差值
	 * @param code
	 * @param date
	 * @return
	 */
	PanoramicDataVerificationDto findThisMonthBiosByDate(String code,String date);
	
	/**
	 * 指定时间查询上月度偏差值
	 * @param code
	 * @param date
	 * @return
	 */
	PanoramicDataVerificationDto findLastMonthBiosByDate(String code,String date);
	
	/**
	 * 指定时间的数据值
	 * @param code
	 * @param date
	 * @return
	 */
	PanoramicDataVerificationDto findContentByDate(String code,String date);
}
