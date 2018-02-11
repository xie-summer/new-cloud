package com.monitor.mapper.productmaterials;

import com.cloud.core.Mapper;
import com.monitor.dto.productmaterials.PanoramicProductMaterialsDto;
import com.monitor.dto.realtimeconsumption.PanoramicRealTimeConsumptionDto;
import com.monitor.model.productmaterials.PanoramicProductMaterials;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author summer
 */
@Repository("productMaterialsMapper")
public interface PanoramicProductMaterialsMapper extends Mapper<PanoramicProductMaterials> {
    /**
     * 查询产品分类
     * @return
     */
    @Select("SELECT SUBSTRING(CODE, 1, 12) CODE ,NAME FROM PANORAMIC_PRODUCT_MATERIALS AS B GROUP BY SUBSTRING(CODE, 1, 12)")
    List<PanoramicProductMaterials> listRealTimeProductSummaryCategoryTask();

    /**
     * 根据时间和code统计进出库总值（进库-出库）
     *
     * @param code
     * @param date
     * @param type
     * @return
     */
    @Select("select SUM(value) as value from panoramic_product_materials where code=#{code} and date_format(utime,'%Y%m%d') =date_format(#{date},'%Y%m%d') and in_or_out=#{type} LIMIT 1 ")
    Double summaryByCodeAndDate(@Param("code") String code, @Param("date")String date, @Param("type")Integer type);

}
