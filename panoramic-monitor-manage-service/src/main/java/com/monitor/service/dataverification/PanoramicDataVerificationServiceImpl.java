package com.monitor.service.dataverification;

import com.monitor.mapper.dataverification.PanoramicDataVerificationMapper;
import com.monitor.model.dataverification.PanoramicDataVerification;
import com.monitor.api.dataverification.PanoramicDataVerificationService;
import com.monitor.dto.dataverification.PanoramicDataVerificationDto;
import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 
 * @author gang
 *
 */
@Service("dataVerificationService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicDataVerificationServiceImpl extends AbstractService<PanoramicDataVerification> implements PanoramicDataVerificationService {
    @Autowired
    @Qualifier("dataVerificationMapper")
    private PanoramicDataVerificationMapper panoramicDataVerificationMapper;

	/**
	 * 磷钙
	 */
	public static final String HG_01_XY_7505 = "HG01XY7505";
	public static final String NAME_01_XY_7505 = "磷钙";
	
	/**
	 * 普钙
	 */
	public static final String HG_01_XY_7504 = "HG01XY7504";
	public static final String NAME_01_XY_7504 = "普钙";
	
	/**
	 * 磷矿粉
	 */
	public static final String HG_01_XY_7500 = "HG01XY7500";
	public static final String NAME_01_XY_7500 = "磷矿粉";
	
    /**
     * 指定时间查询月度偏差值
     */
	@Override
	public PanoramicDataVerificationDto findThisMonthBiosByDate(String code,String date) {
		
		String materialName;
		
		if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7500)) {
			materialName = NAME_01_XY_7500;
		} else if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7504)) {
			materialName = NAME_01_XY_7504;
		} else if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7505)) {
			materialName = NAME_01_XY_7505;
		} else {
			materialName = null;
		}
		
		//指定时间获取本月度的采集计量值和出入库计量值
		double valueAuto = panoramicDataVerificationMapper.findThisMonthAutoSummary(materialName,date) == null ? 0:
					panoramicDataVerificationMapper.findThisMonthAutoSummary(materialName,date).doubleValue();
		
		double valueManual =  panoramicDataVerificationMapper.findThisMonthManualSummary(materialName,date) == null ? 0:
					panoramicDataVerificationMapper.findThisMonthManualSummary(materialName,date).doubleValue();
		double bios;
		PanoramicDataVerificationDto result = new PanoramicDataVerificationDto();
		
		if(valueAuto == 0) {
			bios = 0;
		} else {
			bios = Math.round((valueAuto - valueManual)/ valueAuto * 100)/100 ;
		}
		
		result.setValueAuto(valueAuto);
		result.setValueManual(valueManual);
		result.setBias(bios);
		
		return result;
	}

	/**
	 * 指定时间查询上月度偏差值
	 */
	@Override
	public PanoramicDataVerificationDto findLastMonthBiosByDate(String code,String date) {
		
String materialName;
		
		if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7500)) {
			materialName = NAME_01_XY_7500;
		} else if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7504)) {
			materialName = NAME_01_XY_7504;
		} else if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7505)) {
			materialName = NAME_01_XY_7505;
		} else {
			materialName = null;
		}
		
		//指定时间获取本月度的采集计量值和出入库计量值
		double valueAuto = panoramicDataVerificationMapper.findLastMonthAutoSummary(materialName,date) == null? 0:
			panoramicDataVerificationMapper.findLastMonthAutoSummary(materialName,date).doubleValue();
		
		double valueManual =  panoramicDataVerificationMapper.findLastMonthAutoSummary(materialName,date) == null? 0:
			panoramicDataVerificationMapper.findLastMonthAutoSummary(materialName,date).doubleValue();
		
		double bios;
		PanoramicDataVerificationDto result = new PanoramicDataVerificationDto();
		
		if(valueAuto == 0) {
			bios = 0;
		} else {
			bios = Math.round((valueAuto - valueManual)/ valueAuto * 100)/100 ;
		}
		
		result.setValueAuto(valueAuto);
		result.setValueManual(valueManual);
		result.setBias(bios);
		
		return result;
	}

	@Override
	public PanoramicDataVerificationDto findContentByDate(String code, String date) {
		
		PanoramicDataVerificationDto result = new PanoramicDataVerificationDto();
		
		if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7500)) {
			result = panoramicDataVerificationMapper.findContentByDate(date,NAME_01_XY_7500);
		} else if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7504)) {
			result = panoramicDataVerificationMapper.findContentByDate(date,NAME_01_XY_7504);
		} else if(StringUtils.equalsIgnoreCase(code, HG_01_XY_7505)) {
			result = panoramicDataVerificationMapper.findContentByDate(date,NAME_01_XY_7505);
		} else {
			return null;
		}
		
		return result;
	}
}
