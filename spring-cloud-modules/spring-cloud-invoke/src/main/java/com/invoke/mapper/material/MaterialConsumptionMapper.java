package com.invoke.mapper.material;

import com.cloud.core.Mapper;
import com.invoke.model.dto.MaterialConsumptionDto;
import com.invoke.model.material.MaterialConsumption;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author sunmer
 */
@Repository("materialConsumptionMapper")
public interface MaterialConsumptionMapper extends Mapper<MaterialConsumption> {
    /**
     * 根据code和time查询
     *
     * @param matCode
     * @param conTime
     * @return
     */
    @Select("SELECT * FROM HR_MATERIAL_CONSUMPTION_MID AS m WHERE m.MAT_CODE = #{matCode} and m.CON_TIME >=#{conTime}")
    List<MaterialConsumptionDto> listByDate(@Param("matCode") String matCode, @Param("conTime") Date conTime);
}
