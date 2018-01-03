package com.monitor.service.productofflinemeasurement;

import com.monitor.mapper.productofflinemeasurement.PanoramicProductOfflineMeasurementMapper;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;
import com.monitor.api.productofflinemeasurement.PanoramicProductOfflineMeasurementService;
import com.monitor.dto.productmaterials.PanoramicProductMaterialsDto;
import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.google.common.base.FinalizablePhantomReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 
 * @author gang
 *
 */
@Service("productOfflineMeasurementService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicProductOfflineMeasurementServiceImpl extends AbstractService<PanoramicProductOfflineMeasurement> implements PanoramicProductOfflineMeasurementService {
	@Autowired
    @Qualifier("productOfflineMeasurementMapper")
    private PanoramicProductOfflineMeasurementMapper panoramicProductOfflineMeasurementMapper;
	
	/**
	 * 磷钙
	 */
	public static final String HG_01_XY_750510 = "HG01XY750510";
	/**
	 * 普钙
	 */
	public static final String HG_01_XY_750410 = "HG01XY750410";
	/**
	 * 时间段
	 */
	public static final String TIME_00 = "00";
	public static final String TIME_07 = "07";
	public static final String TIME_08 = "08";
	public static final String TIME_15 = "15";
	public static final String TIME_16 = "16";
	public static final String TIME_23 = "23";
	public static final String TIME_30 = "30";
	public static final String TIME_31 = "31";
	public static final String TIME_32 = "32";
	/**
	 * 指定时间内磷钙的各时间段下线量
	 */
	@Override
	public List<PanoramicProductMaterialsDto> findCalciumphosphateByDate(String date) {
		List<PanoramicProductMaterialsDto> result = panoramicProductOfflineMeasurementMapper.listTimelyByDate(date,HG_01_XY_750510);
		
		//数据库后去早中晚的合计数据
		PanoramicProductMaterialsDto eveningResult = 
				panoramicProductOfflineMeasurementMapper.listTimelyAmountByDate(HG_01_XY_750510,date,TIME_00,TIME_07);
		
		PanoramicProductMaterialsDto morningResult = 
				panoramicProductOfflineMeasurementMapper.listTimelyAmountByDate(HG_01_XY_750510,date,TIME_08,TIME_15);
		
		PanoramicProductMaterialsDto noonResult = 
				panoramicProductOfflineMeasurementMapper.listTimelyAmountByDate(HG_01_XY_750510,date,TIME_16,TIME_23);
		
		//分时数据加上各时间段的合计值
		result.add(morningResult);
		result.add(noonResult);
		result.add(eveningResult);
		
		return result;
	}

	/**
	 * 指定时间内普钙的各时间段下线量
	 */
	@Override
	public List<PanoramicProductMaterialsDto> findCalciumsuperphosphateByDate(String date) {
		List<PanoramicProductMaterialsDto> result = panoramicProductOfflineMeasurementMapper.listTimelyByDate(date,HG_01_XY_750410);
		
		//数据库后去早中晚的合计数据
		PanoramicProductMaterialsDto eveningResult = 
				panoramicProductOfflineMeasurementMapper.listTimelyAmountByDate(HG_01_XY_750410,date,TIME_00,TIME_07);
		eveningResult.setHour(TIME_31);
		
		PanoramicProductMaterialsDto morningResult = 
				panoramicProductOfflineMeasurementMapper.listTimelyAmountByDate(HG_01_XY_750410,date,TIME_08,TIME_15);
		morningResult.setHour(TIME_30);
		
		PanoramicProductMaterialsDto noonResult = 
				panoramicProductOfflineMeasurementMapper.listTimelyAmountByDate(HG_01_XY_750410,date,TIME_16,TIME_23);
		noonResult.setHour(TIME_32);
		
		//分时数据加上各时间段的合计值
		result.add(morningResult);
		result.add(noonResult);
		result.add(eveningResult);
		return result;
	}

}
