package com.monitor.web.controller.materialthresholdconfiguration;

import com.cloud.api.vo.ResultCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.api.materialthresholdconfiguration.PanoramicMaterialThresholdConfigurationService;
import com.monitor.model.materialthresholdconfiguration.PanoramicMaterialThresholdConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * @author summer
 * 2017/11/27
 */
@Api
@RestController
@RequestMapping("/material/threshold/configuration")
public class PanoramicMaterialThresholdConfigurationController {
    @Autowired
    @Qualifier("materialThresholdConfigurationService")
    private PanoramicMaterialThresholdConfigurationService materialThresholdConfigurationService;

    /**
     * @param panoramicMaterialThresholdConfiguration
     * @return
     */
    @PostMapping("/{id}")
    public ResultCode<PanoramicMaterialThresholdConfiguration> add(PanoramicMaterialThresholdConfiguration panoramicMaterialThresholdConfiguration) {
        materialThresholdConfigurationService.save(panoramicMaterialThresholdConfiguration);
        return ResultCode.getSuccessReturn(panoramicMaterialThresholdConfiguration);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    /**
     * @param panoramicMaterialThresholdConfiguration
     * @return
     */
    @PutMapping
    public ResultCode<PanoramicMaterialThresholdConfiguration> update(PanoramicMaterialThresholdConfiguration panoramicMaterialThresholdConfiguration) {
        materialThresholdConfigurationService.update(panoramicMaterialThresholdConfiguration);
        return ResultCode.getSuccessReturn(panoramicMaterialThresholdConfiguration);
    }

    /**
     * 根据code和category查询该上下线配置接口
     *
     * @param code
     * @param category
     * @return
     */
    @ApiOperation(value = "根据code和category查询该上下线配置接口", notes = "根据code和category查询该上下线配置接口")
    @GetMapping("/{category}/{code}")
    public ResultCode<PanoramicMaterialThresholdConfiguration> findByCode(@PathVariable("category") String category, @PathVariable("code") String code) {
        PanoramicMaterialThresholdConfiguration materialThresholdConfiguration = materialThresholdConfigurationService.findByCode(category, code);
        return ResultCode.getSuccessReturn(materialThresholdConfiguration);
    }

    /**
     * 查询所有物料上下线配置
     *
     * @return
     */
    @ApiOperation(value = "查询所有物料上下线配置接口", notes = "查询所有物料上下线配置接口")
    @GetMapping("/list")
    public ResultCode<List<PanoramicMaterialThresholdConfiguration>> listAll() {
        List<PanoramicMaterialThresholdConfiguration> list = materialThresholdConfigurationService.findAll();
        return ResultCode.getSuccessReturn(list);
    }

    /**
     * 根据category查询所有物料上下线配置
     *
     * @param category
     * @return
     */
    @ApiOperation(value = "根据category查询所有物料上下线配置接口", notes = "根据category查询所有物料上下线配置")
    @GetMapping("/list/{category}")
    public ResultCode<List<PanoramicMaterialThresholdConfiguration>> listByCategory(@PathVariable("category") String category) {
        Condition condition = new Condition(PanoramicMaterialThresholdConfiguration.class, false);
        condition.createCriteria().andCondition("category ='" + category + "' and status =1 and delete_flag =1");
        List<PanoramicMaterialThresholdConfiguration> list = materialThresholdConfigurationService.findByCondition(condition);
        return ResultCode.getSuccessReturn(list);
    }
}
