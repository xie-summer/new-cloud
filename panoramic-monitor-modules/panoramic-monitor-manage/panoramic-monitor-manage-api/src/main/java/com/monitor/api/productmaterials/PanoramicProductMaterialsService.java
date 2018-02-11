package com.monitor.api.productmaterials;

import com.cloud.core.Service;
import com.monitor.dto.productmaterials.PanoramicProductMaterialsDto;
import com.monitor.model.productmaterials.PanoramicProductMaterials;

import java.util.List;


/**
 * @author summer
 * 2017/11/21.
 */
public interface PanoramicProductMaterialsService extends Service<PanoramicProductMaterials> {


    /**
     * 产品实时汇总定时任务
     */
    void realTimeProductSummaryTask();
    /**
     * 查询产品分类-定时任务
     *
     * @return
     */
    List<PanoramicProductMaterials> listRealTimeProductSummaryCategoryTask();

    /**
     * 产品实时数据汇总-定时任务
     *
     * @param name
     * @param code
     * @param date
     */
    void productSummaryTask(String name, String code, String date);

    /**根据时间和code统计进出库总值（进库-出库）
     * @param code
     * @param date
     * @return
     */
    Double summaryByCodeAndDate(String code, String date);
      
}
