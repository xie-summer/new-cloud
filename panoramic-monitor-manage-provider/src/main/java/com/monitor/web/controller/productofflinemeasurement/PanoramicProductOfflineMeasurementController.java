package com.monitor.web.controller.productofflinemeasurement;

import com.cloud.api.vo.ResultCode;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;
import com.monitor.model.realtimeconsumption.PanoramicRealTimeConsumption;

import com.monitor.web.controller.base.AbstractAnnotationController;
import io.swagger.annotations.ApiOperation;

import com.monitor.api.productofflinemeasurement.PanoramicProductOfflineMeasurementService;
import com.monitor.dto.dataverification.PanoramicDataVerificationDto;
import com.monitor.dto.productionmonitoring.Productionmonitoringinfo;
import com.monitor.dto.productmaterials.PanoramicProductMaterialsDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author gang
* 2017/12/29.
*/
@RestController
@RequestMapping("/panoramic/product/offline/measurement")
public class PanoramicProductOfflineMeasurementController extends AbstractAnnotationController{
   @Autowired
   @Qualifier("productOfflineMeasurementService")
    private PanoramicProductOfflineMeasurementService panoramicProductOfflineMeasurementService;

    @PostMapping
    public ResultCode<PanoramicProductOfflineMeasurement> add(PanoramicProductOfflineMeasurement panoramicProductOfflineMeasurement) {
        panoramicProductOfflineMeasurementService.save(panoramicProductOfflineMeasurement);
        return ResultCode.getSuccessReturn(panoramicProductOfflineMeasurement);
    }

    @DeleteMapping("/{id}")
    public ResultCode<PanoramicProductOfflineMeasurement> delete(@PathVariable Integer id) {
    		return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicProductOfflineMeasurement> update(PanoramicProductOfflineMeasurement panoramicProductOfflineMeasurement) {
        panoramicProductOfflineMeasurementService.update(panoramicProductOfflineMeasurement);
        return ResultCode.getSuccessReturn(panoramicProductOfflineMeasurement);
    }
    @GetMapping("/{id}")
    public ResultCode<PanoramicProductOfflineMeasurement> detail(@PathVariable Integer id) {
        PanoramicProductOfflineMeasurement panoramicProductOfflineMeasurement = panoramicProductOfflineMeasurementService.findById(id);
        return ResultCode.getSuccessReturn(panoramicProductOfflineMeasurement);
    }

    @GetMapping
    public ResultCode<List<PanoramicProductOfflineMeasurement>> list() {
        List<PanoramicProductOfflineMeasurement> list = panoramicProductOfflineMeasurementService.findAll();
        return ResultCode.getSuccessReturn(list);
    }
    
    @ApiOperation(value = "产品库下线内容查询磷钙", notes = "根据时间查询磷钙内容")
    @GetMapping("/content/calciumphosphate/{date}")
    public ResultCode<List<PanoramicProductMaterialsDto>> realtime(@PathVariable String date) {
    		List<PanoramicProductMaterialsDto> panoramicProductionMonitoring = 
    				panoramicProductOfflineMeasurementService.findCalciumphosphateByDate(date);
        return ResultCode.getSuccessReturn(panoramicProductionMonitoring);
    }
    
    @ApiOperation(value = "产品库下线内容查询普钙", notes = "根据时间查询普钙内容")
    @GetMapping("/content/calciumsuperphosphate/{date}")
    public ResultCode<List<PanoramicProductMaterialsDto>> content(@PathVariable String date) {
    	List<PanoramicProductMaterialsDto> panoramicProductionMonitoring = 
    			panoramicProductOfflineMeasurementService.findCalciumsuperphosphateByDate(date);
        return ResultCode.getSuccessReturn(panoramicProductionMonitoring);
    }
}
