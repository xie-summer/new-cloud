package com.risk.warning.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.cloud.core.Mapper;
import com.risk.warning.model.PanoramicSystemWarningReceiver;

@Repository("PanormicSystemWarningReceiverMapper")
public interface PanoramicSystemWarningReceiverMapper extends Mapper<PanoramicSystemWarningReceiver> {

    @Select(" Select ID,WarningConfigurationID,UserID,UserName,Email from panoramic_warning_receiver where WarningConfigurationID = ${WarningConfigurationID} ")
    List<PanoramicSystemWarningReceiver> GetDataByWarningConfigurationID(@Param("WarningConfigurationID") Integer warningconfigurationid);
    
}