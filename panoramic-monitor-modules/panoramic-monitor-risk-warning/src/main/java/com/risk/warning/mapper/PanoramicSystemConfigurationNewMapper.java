package com.risk.warning.mapper;

import com.cloud.core.Mapper;
import com.risk.warning.model.PanoramicSystemConfigurationnew;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("systemConfigurationnewMapper")
public interface PanoramicSystemConfigurationnewMapper extends Mapper<PanoramicSystemConfigurationnew> {

    @Select(" Select * from panoramic_system_configurationnew where Available = 1 ")
    List<PanoramicSystemConfigurationnew> getStrToSql();
}