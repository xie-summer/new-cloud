package com.monitor.service.realtimeconsumption;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.monitor.api.realtimeconsumption.PanoramicRealTimeConsumptionService;
import com.monitor.dto.realtimeconsumption.PanoramicRealTimeConsumptionDto;
import com.monitor.mapper.realtimeconsumption.PanoramicRealTimeConsumptionMapper;
import com.monitor.mapper.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherMapper;
import com.monitor.model.realtimeconsumption.PanoramicRealTimeConsumption;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author summer 2017/11/21.
 */
@Service("realTimeConsumptionService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicRealTimeConsumptionServiceImpl extends AbstractService<PanoramicRealTimeConsumption>
        implements PanoramicRealTimeConsumptionService {

    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(PanoramicRealTimeConsumptionServiceImpl.class);
    @Autowired
    @Qualifier("realTimeConsumptionMapper")
    private PanoramicRealTimeConsumptionMapper realTimeConsumptionMapper;
    @Autowired
    @Qualifier("realTimeConsumptionGatherMapper")
    private PanoramicRealTimeConsumptionGatherMapper realTimeConsumptionGatherMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void realtimeConsumptionSummaryTask(String name, String code, String date) {
        // 先查出来，再去更新
        Condition condition = new Condition(PanoramicRealTimeConsumption.class, false);
        condition.createCriteria().andCondition(
                "  substring(code, 1, 12) = '" + code + "' AND f_id=2 AND delete_flag=1 "
                        + " AND date_format(utime,'%Y%m%d%H') = date_format('" + date + "','%Y%m%d%H')");
        List<PanoramicRealTimeConsumption> consumptionList = realTimeConsumptionMapper.selectByCondition(condition);
        PanoramicRealTimeConsumption record = new PanoramicRealTimeConsumption();
        record.setCode(code);
        record.setName(name);
        record.setValue(0.0);
        record.setCtime(DateUtil.getCurFullTimestamp());
        record.setId(null);
        record.setOperator("auto_task");
        record.setfId("2");
        record.setUnit(StringUtils.containsIgnoreCase(code, "0004A4000009") ? "度" : "吨");
        record.setDeleteFlag(1);
        final double[] sumValue = {0.0};
        if (null != consumptionList && consumptionList.size() > 0) {
            consumptionList.forEach(e -> {
                sumValue[0] += e.getValue();
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
        	selectOne.setValue(sumValue[0] / 1000.0);
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
            gather.setValue(sumValue[0] / 1000.0);
            realTimeConsumptionGatherMapper.insert(gather);
        }

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicRealTimeConsumption> listRealTimeConsumptionCategoryTask() {
        List<PanoramicRealTimeConsumption> recordList = realTimeConsumptionMapper.listRealTimeConsumptionCategory();
        return recordList;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void realTimeConsumptionSummaryTask() {
        try {
            String date = DateUtil.currentTimeHourStr();
            List<PanoramicRealTimeConsumption> consumptionCategoryList = this.listRealTimeConsumptionCategoryTask();
            if (null == consumptionCategoryList || consumptionCategoryList.size() == 0) {
                DB_LOGGER.warn("实时消耗表数据为空{}");
                return;
            }
            consumptionCategoryList.forEach((PanoramicRealTimeConsumption e) -> {
                this.realtimeConsumptionSummaryTask(e.getName(), e.getCode(), date);
            });
        } catch (Exception e) {
            DB_LOGGER.warn("实时消耗数据汇总到汇总表{},出现异常" + e);
        }
    }
}
