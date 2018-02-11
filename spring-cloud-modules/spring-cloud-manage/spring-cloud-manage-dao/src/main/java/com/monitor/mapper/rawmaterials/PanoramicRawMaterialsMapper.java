package com.monitor.mapper.rawmaterials;

import com.cloud.core.Mapper;
import com.monitor.model.rawmaterials.PanoramicRawMaterials;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author summer
 */
@Repository("rawMaterialsMapper")
public interface PanoramicRawMaterialsMapper extends Mapper<PanoramicRawMaterials> {
    /**
     * 根据时间和code统计进出库总值（进库-出库）
     *
     * @param code
     * @param date
     * @param type
     * @return
     */
    Double summaryByCodeAndDate(@Param("code") String code, @Param("date") String date, @Param("type") Integer type);

    /**
     * 查询原理分类
     *
     * @return
     */
    @Select("SELECT SUBSTRING(CODE, 1, 12) CODE ,NAME FROM PANORAMIC_RAW_MATERIALS AS B GROUP BY SUBSTRING(CODE, 1, 12)")
    List<PanoramicRawMaterials> listSummaryCategory();
}