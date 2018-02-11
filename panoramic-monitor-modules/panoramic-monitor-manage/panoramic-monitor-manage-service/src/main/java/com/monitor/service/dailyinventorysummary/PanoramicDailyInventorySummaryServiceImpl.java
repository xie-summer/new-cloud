package com.monitor.service.dailyinventorysummary;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.cloud.util.DateUtil;
import com.cloud.util.MathUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.monitor.api.dailyinventorysummary.PanoramicDailyInventorySummaryService;
import com.monitor.api.materialthresholdconfiguration.PanoramicMaterialThresholdConfigurationService;
import com.monitor.api.productmaterials.PanoramicProductMaterialsService;
import com.monitor.api.rawmaterials.PanoramicRawMaterialsService;
import com.monitor.api.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherService;
import com.monitor.mapper.dailyinventorysummary.PanoramicDailyInventorySummaryMapper;
import com.monitor.mapper.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherMapper;
import com.monitor.model.dailyinventorysummary.PanoramicDailyInventorySummary;
import com.monitor.model.materialthresholdconfiguration.PanoramicMaterialThresholdConfiguration;
import com.monitor.model.productmaterials.PanoramicProductMaterials;
import com.monitor.model.rawmaterials.PanoramicRawMaterials;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;
import com.monitor.support.ExceptionRecordStatusEnum;
import com.monitor.support.ThresholdConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author summer 2017/11/21.
 */
@Service("dailyInventorySummaryService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicDailyInventorySummaryServiceImpl extends AbstractService<PanoramicDailyInventorySummary>
        implements PanoramicDailyInventorySummaryService {
    public static final int SUMMARY_CATEGORY = 2;
    @Autowired
    @Qualifier("materialThresholdConfigurationService")
    private PanoramicMaterialThresholdConfigurationService materialThresholdConfigurationService;
    @Autowired
    @Qualifier("dailyInventorySummaryMapper")
    private PanoramicDailyInventorySummaryMapper dailyInventorySummaryMapper;
    @Autowired
    @Qualifier("rawMaterialsService")
    private PanoramicRawMaterialsService rawMaterialsService;
    @Autowired
    @Qualifier("productMaterialsService")
    private PanoramicProductMaterialsService productMaterialsService;
    @Autowired
    @Qualifier("realTimeConsumptionGatherService")
    private PanoramicRealTimeConsumptionGatherService realTimeConsumptionGatherService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public Integer countUsable(String code, String date) {
    		//最近7天历史消耗量数据值获取
    		List<PanoramicRealTimeConsumptionGather> sum = realTimeConsumptionGatherService.findNumberdayData(code, 7, date);
    		double sumSingle = 0.0;
    		
    		if (null != sum && sum.size() != 0) {
    			//当前仓库存量值获取
        		PanoramicDailyInventorySummary summary = queryByDateAndCode(code, date);
        		if (Optional.ofNullable(summary).isPresent()) {
        		  for(int i = 0;i < sum.size();i++) {
        			  sumSingle = MathUtil.add(sumSingle, sum.get(i).getValue());
        		  }
              return Integer.parseInt(new java.text.DecimalFormat("0").format(summary.getValue() / sumSingle * sum.size()));
        		}
        		return null;
    		} else {
    			return null;
    		}
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void dailyInventorySummaryTask(String date, List<PanoramicDailyInventorySummary> records) {
        List<PanoramicDailyInventorySummary> curRecords = Lists.newArrayList();
        //今日库存
        List<PanoramicDailyInventorySummary> summaryList = listByDateAndCode(date);
        saveOrUpdateRecord(date, records, curRecords, summaryList);
    }

    private PanoramicDailyInventorySummary getPanoramicDailyInventorySummary(Double value, Integer category) {
        PanoramicDailyInventorySummary record = new PanoramicDailyInventorySummary();
        record.setValue(value);
        record.setCategory(category);
        record.setUnit("吨");
        record.setCtime(DateUtil.getCurFullTimestamp());
        record.setUtime(record.getCtime());
        record.setDeleteFlag(1);
        record.setDtime(null);
        record.setfId("2");
        record.setId(null);
        record.setOperator("auto_task");
        return record;
    }

    private void saveOrUpdateRecord(String date, List<PanoramicDailyInventorySummary> records, List<PanoramicDailyInventorySummary> curRecords, List<PanoramicDailyInventorySummary> summaryList) {
        if (null != records && records.size() > 0) {
            records.forEach(e -> {
                setRecordValue(date, e);
                e.setId(null);
                curRecords.add(e);
            });
        } else {
            List<PanoramicRawMaterials> rawMaterialsList = rawMaterialsService.listSummaryCategory();
            if (null != rawMaterialsList && rawMaterialsList.size() > 0) {
                rawMaterialsList.forEach((PanoramicRawMaterials e) -> {
                    Double value = rawMaterialsService.summaryByCodeAndDate(e.getCode(), date);
                    value = value < 0 ? 0 : value;
                    PanoramicDailyInventorySummary record = getPanoramicDailyInventorySummary(value, 1);
                    record.setCode(e.getCode());
                    record.setName(e.getName());
                    curRecords.add(record);
                });
            }

            List<PanoramicProductMaterials> categoryList = productMaterialsService.listRealTimeProductSummaryCategoryTask();
            if (null != categoryList && categoryList.size() > 0) {
                categoryList.forEach((PanoramicProductMaterials e) -> {
                    Double value = productMaterialsService.summaryByCodeAndDate(e.getCode(), date);
                    value = value < 0 ? 0 : value;
                    PanoramicDailyInventorySummary record = getPanoramicDailyInventorySummary(value, 2);
                    record.setCode(e.getCode());
                    record.setName(e.getName());
                    curRecords.add(record);
                });
            }
        }
        if (null == summaryList || summaryList.size() == 0) {

            dailyInventorySummaryMapper.insertList(curRecords);
        } else {
            dailyInventorySummaryMapper.updateBatch(curRecords);
        }
    }

    private void setRecordValue(String date, PanoramicDailyInventorySummary e) {
        Double sum = 0.0;
        if (e.getCategory() == SUMMARY_CATEGORY) {
            sum = productMaterialsService.summaryByCodeAndDate(e.getCode(), date);
        } else {
            sum = rawMaterialsService.summaryByCodeAndDate(e.getCode(), date);
        }
        e.setValue((e.getValue() + sum) < 0 ? 0 : (e.getValue() + sum));
        e.setCtime(DateUtil.getCurFullTimestamp());
        e.setUtime(DateUtil.getCurFullTimestamp());
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public PanoramicDailyInventorySummary queryByDateAndCode(String code, String date) {
        Condition condition = new Condition(PanoramicDailyInventorySummary.class, false);
        condition.createCriteria()
                .andCondition(" code ='" + code + "' and f_id=2 and delete_flag=1 and utime >= '"
                        + DateUtil.parseTimestamp(date, "yyyy-MM-dd") + "' and  utime < '"
                        + DateUtil.parseTimestamp(DateUtil.getSpecifiedDayBefor(date, -1), "yyyy-MM-dd") + "'");
        condition.setOrderByClause(" utime desc ");
        List<PanoramicDailyInventorySummary> recordList = dailyInventorySummaryMapper.selectByCondition(condition);
        return (null == recordList || recordList.size() == 0) ? null : recordList.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicDailyInventorySummary> listByDateAndCode(String date) {
        Condition condition = new Condition(PanoramicDailyInventorySummary.class, false);
        condition.createCriteria()
                .andCondition(" f_id=2 and delete_flag=1 and utime >= '" + DateUtil.parseTimestamp(date, "yyyy-MM-dd")
                        + "' and  utime < '"
                        + DateUtil.parseTimestamp(DateUtil.getSpecifiedDayBefor(date, -1), "yyyy-MM-dd") + "'");
        condition.setOrderByClause(" utime desc ");
        List<PanoramicDailyInventorySummary> recordList = dailyInventorySummaryMapper.selectByCondition(condition);
        return recordList;
    }

    @Override
    public List<PanoramicDailyInventorySummary> listByDateAndCode(String date, List<String> codeList) {
        List<PanoramicDailyInventorySummary> records = Lists.newArrayList();
        codeList.forEach((String e) -> {
            PanoramicDailyInventorySummary dailyInventorySummary = this.queryByDateAndCode(e,
                    date);
            records.add(dailyInventorySummary);
        });
        return records;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public Map<String, String> check(List<String> codeList, String date) {
        Map<String, String> result = Maps.newHashMap();
        codeList.forEach((String e) -> {
            PanoramicDailyInventorySummary dailyInventorySummary = this.queryByDateAndCode(e,
                    date);
            PanoramicMaterialThresholdConfiguration configuration = materialThresholdConfigurationService
                    .findByCode(ThresholdConfigConstant.STOCK, e);
            result.put(e, ExceptionRecordStatusEnum.normal.getCode());
            if (!Optional.ofNullable(configuration).isPresent()) {
                // 3 库存配置异常
                result.put(e, ExceptionRecordStatusEnum.configuration.getCode());
            } else if ((!Optional.ofNullable(dailyInventorySummary).isPresent())
                    || dailyInventorySummary.getValue() < configuration.getLowerLimit()) {
                // 1：库存偏低
                result.put(e, ExceptionRecordStatusEnum.low.getCode());
            } else if (dailyInventorySummary.getValue() > configuration.getUpperLimit()) {
                result.put(e, ExceptionRecordStatusEnum.high.getCode());
            }
        });
        return result;
    }


}
