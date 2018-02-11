package com.monitor.api.exceptionrecord;

import com.cloud.core.Service;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;

import java.util.List;


/**
 * 异常信息服务
 *
 * @author summer
 * 2017/11/21.
 */
public interface PanoramicExceptionRecordService extends Service<PanoramicExceptionRecord> {

    /**
     * 根据category和时间 查询异常信息
     *
     * @param category
     * @param startDate
     * @param endDate
     * @return
     */
    List<PanoramicExceptionRecord> listByCategory(String category, String startDate, String endDate);

    /**
     * 根据时间查询全部异常信息
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<PanoramicExceptionRecord> listByDate(String startDate, String endDate);

    /**
     * 根据category和时间 查询异常信息(endDate前3天)
     *
     * @param category
     * @param startDate
     * @return
     */
    List<PanoramicExceptionRecord> findMsgByDate(String category, String startDate);
}
