package com.risk.warning.mapper;

import com.cloud.core.Mapper;
import com.risk.warning.model.PanoramicSystemWarningSourceData;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @author summer
 */
@Repository("PanormicSystemWarningSourceDataMapper")
public interface PanoramicSystemWarningSourceDataMapper extends Mapper<PanoramicSystemWarningSourceData> {

    @Select(" ${strquery} ")
    List<PanoramicSystemWarningSourceData> GetSourceData(@Param("strquery") String strquery);
    
    @Insert(" Insert into panoramic_warning_data (EventName,StrEvent,EventValue,Status,Ctime,SourceID，WarnConfigurationID) values (${EventName},${StrEvent},${EventValue},${Status},${Ctime},${SourceID} , ${WarnConfigurationID}) ")
    void AddWarningSource(@Param("EventName") String eventname,@Param("StrEvent") String strevent,@Param("EventValue") Double eventvalue,@Param("Status") Integer status,@Param("Ctime") Date ctime,@Param("SourceID") Integer sourceid,@Param("WarnConfigurationID") Integer warnconfigurationid);
}