package com.monitor.api.materialthresholdconfiguration;

import com.cloud.core.Service;
import com.monitor.model.materialthresholdconfiguration.PanoramicMaterialThresholdConfiguration;


/**
 * 物料上下线配置
 *
 * @author summer
 * 2017/11/27
 */
public interface PanoramicMaterialThresholdConfigurationService extends Service<PanoramicMaterialThresholdConfiguration> {

    /**
     * 物料上下线内容取得
     * @param code
     * @param category
     * @return
     */
    PanoramicMaterialThresholdConfiguration findByCode(String category,String code);
}
