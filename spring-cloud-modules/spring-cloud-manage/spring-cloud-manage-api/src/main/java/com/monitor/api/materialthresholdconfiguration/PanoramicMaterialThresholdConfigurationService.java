package com.monitor.api.materialthresholdconfiguration;

import com.cloud.core.IService;
import com.monitor.model.materialthresholdconfiguration.PanoramicMaterialThresholdConfiguration;


/**
 * 物料上下线配置
 *
 * @author summer
 * 2017/11/27
 */
public interface PanoramicMaterialThresholdConfigurationService extends IService<PanoramicMaterialThresholdConfiguration> {

    /**
     * 物料上下线内容取得
     * @param code
     * @param category
     * @return
     */
    PanoramicMaterialThresholdConfiguration findByCode(String category,String code);
}
