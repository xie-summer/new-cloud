package com.monitor.service.exceptionrecord;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.cloud.util.DateUtil;
import com.monitor.api.exceptionrecord.PanoramicExceptionRecordService;
import com.monitor.mapper.exceptionrecord.PanoramicExceptionRecordMapper;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;


/**
 * @author summer
 * 2017/11/21.
 */
@Service("exceptionRecordService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicExceptionRecordServiceImpl extends AbstractService<PanoramicExceptionRecord> implements PanoramicExceptionRecordService {
    @Autowired
    @Qualifier("exceptionRecordMapper")
    private PanoramicExceptionRecordMapper exceptionRecordMapper;

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(PanoramicExceptionRecord record){
        record.setDtime(null);
        record.setId(null);
        record.setDeleteFlag(1);
        record.setCtime(record.getCtime() == null ? DateUtil.getCurFullTimestamp() : record.getCtime());
        record.setStatus(0);
        record.setUtime(record.getCtime());
        record.setAlarmTime(DateUtil.getCurTruncTimestamp());
        super.save(record);
    }

    /**
     * 方法上不需要事务
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicExceptionRecord> listByCategory(String category, String startDate, String endDate) {
        List<PanoramicExceptionRecord> recordList = getPanoramicExceptionRecords(category, startDate, endDate);
        return recordList;
    }

    private List<PanoramicExceptionRecord> getPanoramicExceptionRecords(String category, String startDate, String endDate) {
        Condition condition = new Condition(PanoramicExceptionRecord.class, false);
        StringBuilder sb = new StringBuilder("delete_flag=1 and alarm_time >='" + startDate + "' and alarm_time <='" + DateUtil.getSpecifiedDayBefor(endDate, -1));
        if (StringUtils.isNotBlank(category)) {
            sb.append("'  and alarm_item = '" + category + "'");
        }
        condition.createCriteria().andCondition(sb.toString());
        condition.setOrderByClause(" status desc ");
        condition.setOrderByClause(" alarm_time desc ");
        return exceptionRecordMapper.selectByCondition(condition);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicExceptionRecord> listByDate(String startDate, String endDate) {
        List<PanoramicExceptionRecord> recordList = getPanoramicExceptionRecords(null, startDate, endDate);
        return recordList;
    }

    /**
     * @param category
     * @param startDate
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicExceptionRecord> findMsgByDate(String category, String startDate) {
        List<PanoramicExceptionRecord> recordList = exceptionRecordMapper.findMsgByDate(category, startDate, 3);
        return recordList;
    }
}
