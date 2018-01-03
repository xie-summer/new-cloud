package com.risk.warning.mapper;

import com.cloud.core.Mapper;
import com.risk.warning.model.PanoramicSystemConfigurationNew;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("panoramicSystemConfigurationMappernew")
public interface PanoramicSystemConfigurationNewMapper extends Mapper<PanoramicSystemConfigurationNew> {

    @Select(" Select * from panoramic_system_configurationnew where Available = 1 ")
    List<PanoramicSystemConfigurationNew> GetStrToSql();
}