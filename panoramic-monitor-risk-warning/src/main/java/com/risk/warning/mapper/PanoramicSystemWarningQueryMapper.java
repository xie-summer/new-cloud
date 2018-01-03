package com.risk.warning.mapper;

import com.cloud.core.Mapper;
import com.risk.warning.model.PanoramicSystemWarningQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("PanormicSystemWarningQueryMapper")
public interface PanoramicSystemWarningQueryMapper extends Mapper<PanoramicSystemWarningQuery> {

    @Select(" select WarnConfigurationID,QuerySql,IntervalTime from panoramic_system_sqlquery where Available = 1 ")
    List<PanoramicSystemWarningQuery> GetStrSqlQuery();
}