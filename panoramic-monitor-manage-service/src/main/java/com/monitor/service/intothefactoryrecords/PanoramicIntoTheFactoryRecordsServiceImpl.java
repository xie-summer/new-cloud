package com.monitor.service.intothefactoryrecords;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.cloud.util.DateUtil;
import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import com.google.common.collect.Lists;
import com.monitor.api.exceptionrecord.PanoramicExceptionRecordService;
import com.monitor.api.intothefactoryrecords.PanoramicIntoTheFactoryRecordsService;
import com.monitor.mapper.intothefactoryrecords.PanoramicIntoTheFactoryRecordsMapper;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;
import com.monitor.model.intothefactoryrecords.PanoramicIntoTheFactoryRecords;
import com.monitor.support.ExceptionRecordCategoryConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Optional;


/**
 * @author summer
 * 2017/11/29
 */
@Service("intoTheFactoryRecordsService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicIntoTheFactoryRecordsServiceImpl extends AbstractService<PanoramicIntoTheFactoryRecords> implements PanoramicIntoTheFactoryRecordsService {
    private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(PanoramicIntoTheFactoryRecordsServiceImpl.class);

    @Autowired
    @Qualifier("intoTheFactoryRecordsMapper")
    private PanoramicIntoTheFactoryRecordsMapper intoTheFactoryRecordsMapper;
    @Autowired
    @Qualifier("exceptionRecordService")
    private PanoramicExceptionRecordService exceptionRecordService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicIntoTheFactoryRecords> listByDate(String date) {
        Condition condition = new Condition(PanoramicIntoTheFactoryRecords.class, false);
        condition.createCriteria().andCondition(" delete_flag=1  and status=1 and date_format(snapshot_time,'%Y%m%d') = date_format('" + date + "','%Y%m%d')  and err_msg is not null");
        condition.setOrderByClause(" snapshot_time desc ");
        List<PanoramicIntoTheFactoryRecords> factoryRecords = intoTheFactoryRecordsMapper.selectByCondition(condition);
        return factoryRecords;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public PanoramicIntoTheFactoryRecords findByDate(String date) {
        Condition condition = new Condition(PanoramicIntoTheFactoryRecords.class, false);
        condition.createCriteria().andCondition(" delete_flag=1 and status=1  and date_format(snapshot_time,'%Y%m%d') = date_format('" + date + "','%Y%m%d') and err_msg is not null");
        condition.setOrderByClause(" snapshot_time desc ");
        List<PanoramicIntoTheFactoryRecords> factoryRecords = intoTheFactoryRecordsMapper.selectByCondition(condition);
        return (null == factoryRecords || factoryRecords.size() == 0) ? null : factoryRecords.get(0);
    }

    @Override
    public Integer count(String date) {
        Condition condition = new Condition(PanoramicIntoTheFactoryRecords.class, false);
        condition.createCriteria().andCondition(" delete_flag=1 and err_msg is not null and status=1 and date_format(snapshot_time,'%Y%m%d') = date_format('" + date + "','%Y%m%d') ");
        Integer count = intoTheFactoryRecordsMapper.selectCountByCondition(condition);
        return count;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void regularlyRefreshTask() {
        Condition condition = new Condition(PanoramicIntoTheFactoryRecords.class, false);
        condition.createCriteria().andCondition(" err_msg is null and delete_flag=1 and status=1  and date_format(snapshot_time,'%Y%m%d') = date_format('" + DateUtil.currentTimeStr() + "','%Y%m%d') ");
        condition.setOrderByClause(" snapshot_time desc ");
        List<PanoramicIntoTheFactoryRecords> records = intoTheFactoryRecordsMapper.selectByCondition(condition);
        if (null == records || records.size() == 0) {
            return;
        }
        List<PanoramicExceptionRecord> exceptionRecords = Lists.newArrayList();
        records.forEach(e -> {

            try {
                if (null == e.getNetWeight() || e.getNetWeight() == 0) {
                    e.setErrMsg("数据异常，记录数据没有净重值");

                }
                if (e.getTare() / e.getNetWeight() >= 0.006) {
                    e.setErrMsg("超重");
                }
                if (e.getTare() / e.getNetWeight() <= -0.006) {
                    e.setErrMsg("缺重");
                }
                if (StringUtils.isNotBlank(e.getErrMsg())) {
                    PanoramicExceptionRecord exceptionRecord = new PanoramicExceptionRecord();
                    exceptionRecord.setStatus(0);
                    exceptionRecord.setOperator("auto_task");
                    exceptionRecord.setId(null);
                    exceptionRecord.setDtime(null);
                    exceptionRecord.setCtime(DateUtil.getCurFullTimestamp());
                    exceptionRecord.setUtime(exceptionRecord.getCtime());
                    exceptionRecord.setAlarmTime(exceptionRecord.getCtime());
                    exceptionRecord.setAlarmContent(e.getErrMsg());
                    exceptionRecord.setAssociatedPerson(null);
                    exceptionRecord.setDeleteFlag(1);
                    exceptionRecord.setAlarmItem(ExceptionRecordCategoryConstant.IN_OR_OUT_EXCEPTION_RECORD);
                    exceptionRecords.add(exceptionRecord);
                }
            } catch (Exception e1) {
                DB_LOGGER.error("数据异常，记录数据没有净重值");
            }

            if (Optional.ofNullable(e.getErrMsg()).isPresent()) {
                e.setMemo("<-- auto task {\"更新异常信息\"}:" + e.getErrMsg() + DateUtil.currentTimeStr() + "-->");
                e.setUtime(DateUtil.getCurFullTimestamp());
            }
            intoTheFactoryRecordsMapper.updateByPrimaryKeySelective(e);
        });
        if (null != exceptionRecords && exceptionRecords.size() > 0) {
            exceptionRecordService.save(exceptionRecords);
        }
    }
}
