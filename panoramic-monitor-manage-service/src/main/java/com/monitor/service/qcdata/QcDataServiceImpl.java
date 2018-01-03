package com.monitor.service.qcdata;

import com.cloud.core.AbstractService;
import com.cloud.core.ServiceException;
import com.monitor.api.materialthresholdconfiguration.PanoramicMaterialThresholdConfigurationService;
import com.monitor.api.qcdata.QcDataService;
import com.monitor.mapper.qcdata.QcDataMapper;
import com.monitor.model.qcdata.QcData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author summer
 * 2017/11/27
 */
@Service("qcDataService")
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class QcDataServiceImpl extends AbstractService<QcData> implements QcDataService {
    /**
     * 质检数据例外等级（判断是够合格标志）
     */
    private final static String STATUS = "合格";

    @Autowired
    @Qualifier("materialThresholdConfigurationService")
    private PanoramicMaterialThresholdConfigurationService materialThresholdConfigurationService;
    @Autowired
    @Qualifier("qcDataMapper")
    private QcDataMapper qcDataMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public List<QcData> listByDate(String date, String type) {
        return qcDataMapper.listByDate(date, type);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public Double passRate(String date, String type) {
        List<QcData> dataList = qcDataMapper.listByDate(date, type);
        if (null == dataList || dataList.size() == 0) {
            return null;
        }
        final double[] count = {0};
        dataList.forEach(e -> {
            if (STATUS.equalsIgnoreCase(e.getEventLevel())) {
                count[0]++;
            }
        });
        return count[0] / dataList.size();
    }
}
