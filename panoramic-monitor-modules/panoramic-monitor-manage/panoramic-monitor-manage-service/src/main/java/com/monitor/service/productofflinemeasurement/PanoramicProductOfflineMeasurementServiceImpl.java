package com.monitor.service.productofflinemeasurement;

import com.monitor.mapper.productofflinemeasurement.PanoramicProductOfflineMeasurementMapper;
import com.monitor.mapper.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherMapper;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;
import com.monitor.service.realtimeconsumption.PanoramicRealTimeConsumptionServiceImpl;
import tk.mybatis.mapper.entity.Condition;
import com.monitor.api.productofflinemeasurement.PanoramicProductOfflineMeasurementService;
import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author gang
 *
 */
@Service("productOfflineMeasurementService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicProductOfflineMeasurementServiceImpl extends AbstractService<PanoramicProductOfflineMeasurement> implements PanoramicProductOfflineMeasurementService {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(PanoramicRealTimeConsumptionServiceImpl.class);
    
	@Autowired
    @Qualifier("productOfflineMeasurementMapper")
    private PanoramicProductOfflineMeasurementMapper panoramicProductOfflineMeasurementMapper;
	
	@Autowired
    @Qualifier("realTimeConsumptionGatherMapper")
    private PanoramicRealTimeConsumptionGatherMapper realTimeConsumptionGatherMapper;

	/**
	 * 产品下线定时任务
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void productOfflineMeasurementSummaryTask() {
		try {
            String date = DateUtil.currentTimeHourStr();
            List<PanoramicProductOfflineMeasurement> productOfflineCategoryList = 
            		panoramicProductOfflineMeasurementMapper.listProductOfflineCategory();
            
            if (null == productOfflineCategoryList || productOfflineCategoryList.size() == 0) {
                DB_LOGGER.warn("产品下线表数据为空{}");
                return;
            }
            
            productOfflineCategoryList.forEach((PanoramicProductOfflineMeasurement e) -> {
                this.productOfflineSummaryTask(e.getName(), e.getCode(), date);
            });
        } catch (Exception e) {
            DB_LOGGER.warn("产品下线表数据汇总到汇总表{},出现异常" + e);
        }
	}

	/**
	 * 产品消耗数据实时汇总
	 * @param name
	 * @param code
	 * @param date
	 */
	private void productOfflineSummaryTask(String name, String code, String date) {
		
        // 
        Condition condition = new Condition(PanoramicProductOfflineMeasurement.class, false);
        condition.createCriteria().andCondition(
                "	code = '" + code + "' "
                	+	"AND f_id=2 AND delete_flag=1 "
                +	"AND date_format(utime,'%Y%m%d%H') = date_format('" + date + "','%Y%m%d%H')");
        List<PanoramicProductOfflineMeasurement> offlineMeasurementList = 
        		panoramicProductOfflineMeasurementMapper.selectByCondition(condition);
        
        PanoramicProductOfflineMeasurement record = new PanoramicProductOfflineMeasurement();
        record.setCode(code);
        record.setName(name);
        record.setValue(String.valueOf("0.0"));
        record.setCtime(DateUtil.getCurFullTimestamp());
        record.setId(null);
        record.setOperator("auto_task");
        record.setfId("2");
        record.setUnit("吨");
        record.setDeleteFlag(1);
        
        final double[] sumValue = {0.0};
        if (null != offlineMeasurementList && offlineMeasurementList.size() > 0) {
        	offlineMeasurementList.forEach(e -> {
                sumValue[0] += Double.valueOf(e.getValue()).doubleValue();
                record.setUtime(e.getUtime());
                record.setDtime(null);
                record.setOperator(e.getOperator());
                record.setfId(e.getfId());
                record.setName(e.getName());
                record.setDeleteFlag(e.getDeleteFlag());
                record.setCtime(e.getCtime());
                record.setId(null);
            });
        }
        PanoramicRealTimeConsumptionGather selectOne = realTimeConsumptionGatherMapper.selectByGatherTime(code, date);
        Optional<PanoramicRealTimeConsumptionGather> one = Optional.ofNullable(selectOne);
        if (one.isPresent()) {
        	selectOne.setValue(sumValue[0] );
        	selectOne.setUtime(DateUtil.getCurFullTimestamp());
        	selectOne.setCtime(selectOne.getUtime());
        	selectOne.setOperator("auto_task_update");
        	selectOne.setGatherTime(date);
            realTimeConsumptionGatherMapper.updateByPrimaryKeySelective(selectOne);
        } else {
            PanoramicRealTimeConsumptionGather gather = new PanoramicRealTimeConsumptionGather();
            gather.setCode(code);
            gather.setName(name);
            gather.setDeleteFlag(record.getDeleteFlag());
            gather.setfId(record.getfId());
            gather.setGatherTime(date);
            gather.setId(null);
            gather.setCtime(DateUtil.getCurFullTimestamp());
            gather.setName(record.getName());
            gather.setOperator(record.getOperator());
            gather.setUnit(record.getUnit());
            gather.setDtime(record.getDtime());
            gather.setUtime(gather.getCtime());
            gather.setValue(sumValue[0] );
            realTimeConsumptionGatherMapper.insert(gather);
        }
	}
}
