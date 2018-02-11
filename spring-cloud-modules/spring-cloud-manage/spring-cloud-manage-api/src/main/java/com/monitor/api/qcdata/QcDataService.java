package com.monitor.api.qcdata;

import com.cloud.core.Service;
import com.monitor.model.qcdata.QcData;

import java.util.List;


/**
 * @author summer
 * 2017/11/27
 */
public interface QcDataService extends Service<QcData> {

    /**根据时间和类型指定查询质检数据
     * @param date
     * @param type
     * @return
     */
    List<QcData> listByDate(String date,String type);

    /**质检合格率查询
     * @param date
     * @param type
     * @return
     */
    Double passRate(String date,String type);
}
