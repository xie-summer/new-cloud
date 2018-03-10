package com.risk.warning.mapper;

import com.cloud.core.IMapper;
import com.risk.warning.model.PanoramicSystemConfigurationNew;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("systemConfigurationnewMapper")
public interface PanoramicSystemConfigurationNewMapper extends IMapper<PanoramicSystemConfigurationNew> {

    @Select(" Select * from panoramic_system_configurationnew where Available = 1 ")
    List<PanoramicSystemConfigurationNew> getStrToSql();
}