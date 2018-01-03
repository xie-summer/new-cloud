package com.invoke.service.material;

import com.cloud.core.AbstractService;
import com.invoke.api.material.MaterialConsumptionService;
import com.invoke.mapper.material.MaterialConsumptionMapper;
import com.invoke.model.dto.MaterialConsumptionDto;
import com.invoke.model.material.MaterialConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author sunmer
 */
@Service("materialConsumptionService")
@Transactional
public class MaterialConsumptionServiceImpl extends AbstractService<MaterialConsumption> implements MaterialConsumptionService {

    @Autowired
    @Qualifier("materialConsumptionMapper")
    private MaterialConsumptionMapper materialConsumptionMapper;

    @Override
    public List<MaterialConsumptionDto> listByDate(String matCode, Date conTime) {
        List<MaterialConsumptionDto> materialConsumptionList = materialConsumptionMapper.listByDate(matCode, conTime);
        return materialConsumptionList;
    }
}
