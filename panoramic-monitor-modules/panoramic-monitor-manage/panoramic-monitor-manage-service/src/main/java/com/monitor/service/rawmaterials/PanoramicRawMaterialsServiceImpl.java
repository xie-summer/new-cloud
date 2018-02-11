package com.monitor.service.rawmaterials;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.monitor.api.dailyinventorysummary.PanoramicDailyInventorySummaryService;
import com.monitor.api.rawmaterials.PanoramicRawMaterialsService;
import com.monitor.mapper.rawmaterials.PanoramicRawMaterialsMapper;
import com.monitor.model.rawmaterials.PanoramicRawMaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author summer
 * 2017/11/21.
 */
@Service("rawMaterialsService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class PanoramicRawMaterialsServiceImpl extends AbstractService<PanoramicRawMaterials> implements PanoramicRawMaterialsService {
    @Autowired
    @Qualifier("dailyInventorySummaryService")
    PanoramicDailyInventorySummaryService dailyInventorySummaryService;
    @Autowired
    @Qualifier("rawMaterialsMapper")
    private PanoramicRawMaterialsMapper rawMaterialsMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public Integer countUsable(String code, String date) {
        return dailyInventorySummaryService.countUsable(code, date);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public Double summaryByCodeAndDate(String code, String date) {

        double inSum = rawMaterialsMapper.summaryByCodeAndDate(code, date, 1)==null?0:rawMaterialsMapper.summaryByCodeAndDate(code, date, 1);
        double outSum = rawMaterialsMapper.summaryByCodeAndDate(code, date, 0)==null?0:rawMaterialsMapper.summaryByCodeAndDate(code, date, 0);
        return inSum - outSum;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<PanoramicRawMaterials> listSummaryCategory() {
        return rawMaterialsMapper.listSummaryCategory();
    }
}
