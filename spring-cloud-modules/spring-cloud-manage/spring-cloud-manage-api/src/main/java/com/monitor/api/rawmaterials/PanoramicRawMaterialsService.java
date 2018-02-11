package com.monitor.api.rawmaterials;

import com.cloud.core.Service;
import com.monitor.model.rawmaterials.PanoramicRawMaterials;

import java.util.List;


/**
 * @author summer
 * 2017/11/21.
 */
public interface PanoramicRawMaterialsService extends Service<PanoramicRawMaterials> {

    /**
     * 库存预计可使用天数
     *
     * @param code
     * @param date
     * @return
     */
    Integer countUsable(String code, String date);

    /**
     * 根据时间和code统计进出库总值（进库-出库）
     *
     * @param code
     * @param date
     * @return
     */
    Double summaryByCodeAndDate(String code, String date);

    /**统计原理进出库分类分类
     * @return
     */
    List<PanoramicRawMaterials> listSummaryCategory();
}
