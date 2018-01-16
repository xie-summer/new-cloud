package com.monitor.web.controller.productofflinemeasurement;

import com.cloud.api.vo.ResultCode;
import com.monitor.model.productofflinemeasurement.PanoramicProductOfflineMeasurement;
import com.monitor.web.controller.base.AbstractAnnotationController;
import io.swagger.annotations.ApiOperation;
import com.monitor.api.productofflinemeasurement.PanoramicProductOfflineMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author gang
* 2017/12/29.
*/
@RestController
@RequestMapping("/product/offline/measurement")
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
    
    @ApiOperation(value = "下线数据定时任务汇总", notes = "下线数据定时任务汇总")
    @PostMapping("/task")
    public ResultCode<Void> task() {
	    	DB_LOGGER.warn("<--下线数据定时任务汇总  开始-->");
	    	panoramicProductOfflineMeasurementService.productOfflineMeasurementSummaryTask();
	    	DB_LOGGER.warn("<--下线数据定时任务汇总  结束-->");
	    return ResultCode.SUCCESS;
    }
}
