package com.monitor.service.realtimeconsumptiongather;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.cloud.util.DateUtil;
import com.monitor.api.inventoryentry.PanoramicInventoryEntryService;
import com.monitor.api.productmaterials.PanoramicProductMaterialsService;
import com.monitor.api.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherService;
import com.monitor.dto.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherDto;
import com.monitor.mapper.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherMapper;
import com.monitor.model.inventoryentry.PanoramicInventoryEntry;
import com.monitor.model.productmaterials.PanoramicProductMaterials;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * @author summer 2017/11/21.
 */
@Service("realTimeConsumptionGatherService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicRealTimeConsumptionGatherServiceImpl extends AbstractService<PanoramicRealTimeConsumptionGather>
		implements PanoramicRealTimeConsumptionGatherService {
	/**
	 * 磷钙
	 */
	public static final String HG_01_XY_7505 = "HG01XY7505";
	/**
	 * 普钙
	 */
	public static final String HG_01_XY_7504 = "HG01XY7504";
	
	/**
	 * 磷矿粉
	 */
	public static final String HG_01_XY_7500 = "HG01XY7500";
	
	@Autowired
	@Qualifier("inventoryEntryService")
	PanoramicInventoryEntryService inventoryEntryService;
	@Autowired
	@Qualifier("productMaterialsService")
	PanoramicProductMaterialsService productMaterialsService;
	@Autowired
	@Qualifier("realTimeConsumptionGatherMapper")
	private PanoramicRealTimeConsumptionGatherMapper realTimeConsumptionGatherMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
	public List<PanoramicRealTimeConsumptionGather> listByCodeAndDate(String date, String code) {
		Condition condition = new Condition(PanoramicRealTimeConsumptionGather.class, false);
		condition.createCriteria()
				.andCondition(" code ='" + code + "' and f_id=2 and delete_flag=1 and ctime > '"
						+ DateUtil.parseTimestamp(date, "yyyy-MM-dd") + "' and  utime < '"
						+ DateUtil.parseTimestamp(DateUtil.getSpecifiedDayBefor(date, -1), "yyyy-MM-dd") + "'");
		condition.setOrderByClause(" gather_time asc ");
		List<PanoramicRealTimeConsumptionGather> recordList = realTimeConsumptionGatherMapper
				.selectByCondition(condition);
		return recordList;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
	public PanoramicRealTimeConsumptionGather queryMonthlyStatisticsByDate(String date, String code) {
		Condition condition = new Condition(PanoramicRealTimeConsumptionGather.class, false);
		condition.createCriteria().andCondition(" code ='" + code + "' AND f_id=2 AND delete_flag=1 "
				+ " AND date_format(gather_time,'%Y%m') = date_format('" + date + "','%Y%m')");
		condition.setOrderByClause(" gather_time asc ");
		List<PanoramicRealTimeConsumptionGather> recordList = realTimeConsumptionGatherMapper
				.selectByCondition(condition);
		PanoramicRealTimeConsumptionGather gather = new PanoramicRealTimeConsumptionGather();
		gather.setCode(code);
		gather.setValue(0.0);
		gather.setCtime(DateUtil.parseTimestamp(date, "yyyy-MM-dd"));
		gather.setGatherTime(date);
		if (null != recordList && recordList.size() > 0) {
			recordList.forEach(e -> {
				gather.setValue(e.getValue() + gather.getValue());
				gather.setDeleteFlag(e.getDeleteFlag());
				gather.setfId(e.getfId());
				gather.setId(null);
				gather.setName(e.getName());
				gather.setOperator(e.getOperator());
				gather.setUnit(e.getUnit());
				gather.setDtime(null);
				gather.setUtime(e.getUtime());
			});
		}
		return gather;
	}

	/**
	 * 取得指定时间的消耗值
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
	public PanoramicRealTimeConsumptionGatherDto queryDayStatisticsByDate(String date, String code) {
		if (StringUtils.equalsIgnoreCase(code, HG_01_XY_7505)
				|| StringUtils.equalsIgnoreCase(code, HG_01_XY_7504) 
				|| StringUtils.equalsIgnoreCase(code, HG_01_XY_7500)
				) {
			return this.queryStatisticsForDay(date, code);
		}

		Condition condition = new Condition(PanoramicRealTimeConsumptionGather.class, false);
		condition.createCriteria().andCondition(" code ='" + code + "' AND f_id=2 AND delete_flag=1 "
				+ " AND date_format(gather_time,'%Y%m%d') = date_format('" + date + "','%Y%m%d')");
		condition.setOrderByClause(" gather_time asc ");
		List<PanoramicRealTimeConsumptionGather> recordList = realTimeConsumptionGatherMapper
				.selectByCondition(condition);
		PanoramicRealTimeConsumptionGatherDto gather = new PanoramicRealTimeConsumptionGatherDto();
		gather.setCode(code);
		gather.setValue(0.0);
		gather.setRecordValues(0.0);
		gather.setCtime(DateUtil.parseTimestamp(date, "yyyy-MM-dd"));
		gather.setGatherTime(date);
		if (null != recordList && recordList.size() > 0) {
			recordList.forEach(e -> {
				gather.setValue(e.getValue() + gather.getValue());
				gather.setDeleteFlag(e.getDeleteFlag());
				gather.setfId(e.getfId());
				gather.setId(null);
				gather.setName(e.getName());
				gather.setOperator(e.getOperator());
				gather.setUnit(e.getUnit());
				gather.setDtime(null);
				gather.setUtime(e.getUtime());
			});
		}
		return gather;
	}

	private PanoramicRealTimeConsumptionGatherDto queryStatisticsForDay(String date, String code) {
		PanoramicRealTimeConsumptionGatherDto gather = new PanoramicRealTimeConsumptionGatherDto();
		gather.setCode(code);
		gather.setValue(0.0);
		gather.setRecordValues(0.0);
		gather.setCtime(DateUtil.parseTimestamp(date, "yyyy-MM-dd"));
		gather.setGatherTime(date);
		gather.setName(StringUtils.equalsIgnoreCase(code, HG_01_XY_7505) ? "磷钙" : "普钙");
		gather.setDeleteFlag(1);
		gather.setDtime(null);
		gather.setfId("2");
		gather.setId(null);
		Condition condition = new Condition(PanoramicProductMaterials.class, false);
		condition.createCriteria().andCondition("code ='" + code + "' and in_or_out =1 and delete_flag =1 "
				+ " AND date_format(utime,'%Y%m%d') = date_format('" + date + "','%Y%m%d')");
		List<PanoramicProductMaterials> productMaterials = productMaterialsService.findByCondition(condition);

		if (null != productMaterials && productMaterials.size() > 0) {
			productMaterials.forEach(e -> {
				gather.setValue(Double.parseDouble(e.getValue()) + gather.getValue());
				gather.setDeleteFlag(e.getDeleteFlag());
				gather.setfId(e.getfId());
				gather.setId(null);
				gather.setName(e.getName());
				gather.setOperator(e.getOperator());
				gather.setUnit(e.getUnit());
				gather.setDtime(null);
				gather.setUtime(e.getUtime());
			});
		}

		Condition cond = new Condition(PanoramicInventoryEntry.class, false);
		cond.createCriteria().andCondition(" code ='" + code + "' and in_or_out =1 and delete_flag =1  "
				+ " AND date_format(utime,'%Y%m%d') = date_format('" + date + "','%Y%m%d')");
		List<PanoramicInventoryEntry> inventoryEntries = inventoryEntryService.findByCondition(cond);
		if (null != inventoryEntries && inventoryEntries.size() > 0) {
			inventoryEntries.forEach(e -> {
				gather.setRecordValues(Double.parseDouble(e.getValue()) + gather.getRecordValues());
			});
		}

		return gather;
	}

}
