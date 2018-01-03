package com.invoke.api.material;

import com.cloud.core.Service;
import com.invoke.model.dto.MaterialConsumptionDto;
import com.invoke.model.material.MaterialConsumption;

import java.util.Date;
import java.util.List;

/**
 * @author summer
 */
public interface MaterialConsumptionService extends Service<MaterialConsumption> {

    /**
     * 根据 matcode 和时间查询
     *
     * @param matCode
     * @param conTime
     * @return
     */
    List<MaterialConsumptionDto> listByDate(String matCode, Date conTime);
}
