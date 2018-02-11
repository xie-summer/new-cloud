package com.monitor.web.controller.productionmonitoring;

import com.cloud.api.vo.ResultCode;
import com.monitor.api.productionmonitoring.PanoramicProductionMonitoringService;
import com.monitor.dto.productionmonitoring.Productionmonitoringinfo;
import com.monitor.model.productionmonitoring.PanoramicProductionMonitoring;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * 2017/11/21.
 */
@Api
@RestController
@RequestMapping("/production/monitoring")
public class PanoramicProductionMonitoringController {
    @Autowired
    @Qualifier("productionMonitoringService")
    private PanoramicProductionMonitoringService productionMonitoringService;

    @PostMapping("/{id}")
    public ResultCode<PanoramicProductionMonitoring> add(PanoramicProductionMonitoring panoramicProductionMonitoring) {
        productionMonitoringService.save(panoramicProductionMonitoring);
        return ResultCode.getSuccessReturn(panoramicProductionMonitoring);
    }

    @DeleteMapping("/{id}")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicProductionMonitoring> update(PanoramicProductionMonitoring panoramicProductionMonitoring) {
        productionMonitoringService.update(panoramicProductionMonitoring);
        return ResultCode.getSuccessReturn(panoramicProductionMonitoring);
    }

    @GetMapping("/{id}")
    public ResultCode<PanoramicProductionMonitoring> detail(@PathVariable Integer id) {
        PanoramicProductionMonitoring panoramicProductionMonitoring = productionMonitoringService.findById(id);
        return ResultCode.getSuccessReturn(panoramicProductionMonitoring);
    }
    
    @ApiOperation(value = "生产监控内容查询", notes = "根据时间查询各种物料消耗内容")
    @GetMapping("/content/{date}")
    public ResultCode<Productionmonitoringinfo> content(@PathVariable String date) {
    		Productionmonitoringinfo panoramicProductionMonitoring = productionMonitoringService.findByDate(date);
        return ResultCode.getSuccessReturn(panoramicProductionMonitoring);
    }
}
