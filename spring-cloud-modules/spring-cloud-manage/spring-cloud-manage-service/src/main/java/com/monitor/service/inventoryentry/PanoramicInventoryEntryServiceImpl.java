package com.monitor.service.inventoryentry;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.cloud.util.DateUtil;
import com.monitor.api.exceptionrecord.PanoramicExceptionRecordService;
import com.monitor.api.inventoryentry.PanoramicInventoryEntryService;
import com.monitor.mapper.inventoryentry.PanoramicInventoryEntryMapper;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;
import com.monitor.model.inventoryentry.PanoramicInventoryEntry;
import com.monitor.support.ExceptionRecordCategoryConstant;
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
 * 2017/11/30
 */
@Service("inventoryEntryService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicInventoryEntryServiceImpl extends AbstractService<PanoramicInventoryEntry> implements PanoramicInventoryEntryService {
    @Autowired
    @Qualifier("inventoryEntryMapper")
    private PanoramicInventoryEntryMapper inventoryEntryMapper;
    @Autowired
    @Qualifier("exceptionRecordService")
    private PanoramicExceptionRecordService exceptionRecordService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveOrUpdate(List<PanoramicInventoryEntry> panoramicInventoryEntryList) {
        panoramicInventoryEntryList.forEach(e -> {
            e.setOperator("人工录入");
            PanoramicInventoryEntry record = inventoryEntryMapper.selectByCodeAndTime(e.getCode(), e.getCtime(), e.getSchedule());
            if (Optional.ofNullable(record).isPresent()) {
                record.setUtime(DateUtil.getCurFullTimestamp());
                record.setValue(e.getValue());
                record.setOperator(e.getOperator());
                inventoryEntryMapper.updateByPrimaryKeySelective(record);
            } else {
                inventoryEntryMapper.insertSelective(e);
            }
        });
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void manualEntryExceptionRecordTask(String date) {
        Condition condition = new Condition(PanoramicInventoryEntry.class, false);
        condition.createCriteria()
                .andCondition(" f_id=2 and delete_flag=1 and utime > '" + DateUtil.parseTimestamp(date, "yyyy-MM-dd")
                        + "' and  utime < '"
                        + DateUtil.parseTimestamp(DateUtil.getSpecifiedDayBefor(date, -1), "yyyy-MM-dd") + "'");
        Integer count = inventoryEntryMapper.selectCountByCondition(condition);
        if (6 == count) {
            return;
        }
        PanoramicExceptionRecord record = new PanoramicExceptionRecord();
        record.setAlarmItem(ExceptionRecordCategoryConstant.MANUAL_ENTRY_EXCEPTION_RECORD);
        record.setDeleteFlag(1);
        record.setAlarmContent("人工录入模块有数据漏录入");
        record.setAssociatedPerson("当前管理员");
        record.setAlarmTime(DateUtil.parseDate(date));
        record.setCtime(DateUtil.getCurFullTimestamp());
        record.setDtime(null);
        record.setId(null);
        record.setOperator("auto_task");
        record.setRelatedPersonLog("定时任务自动扫描-人工录入异常遗漏数据");
        record.setStatus(0);
        record.setUtime(DateUtil.getCurFullTimestamp());
        exceptionRecordService.save(record);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicInventoryEntry> findByDate(String date) {
        return inventoryEntryMapper.findByDate(date);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicExceptionRecord> findMsgByDate(String date) {
        return exceptionRecordService.findMsgByDate(ExceptionRecordCategoryConstant.MANUAL_ENTRY_EXCEPTION_RECORD, date);
    }

}
