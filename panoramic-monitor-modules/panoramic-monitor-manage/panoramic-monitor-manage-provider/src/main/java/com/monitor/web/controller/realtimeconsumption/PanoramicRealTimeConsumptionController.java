package com.monitor.web.controller.realtimeconsumption;

import com.cloud.api.vo.ResultCode;
import com.monitor.api.realtimeconsumption.PanoramicRealTimeConsumptionService;
import com.monitor.dto.realtimeconsumption.PanoramicRealTimeConsumptionDto;
import com.monitor.model.realtimeconsumption.PanoramicRealTimeConsumption;
import com.monitor.web.controller.base.AbstractAnnotationController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
*@author summer
* 2017/11/21.
*/
@Api
@RestController
@RequestMapping("/real/time/consumption")
public class PanoramicRealTimeConsumptionController extends AbstractAnnotationController {
   @Autowired
   @Qualifier("realTimeConsumptionService")
    private PanoramicRealTimeConsumptionService realTimeConsumptionService;

    @PostMapping
    public ResultCode<PanoramicRealTimeConsumption> add(PanoramicRealTimeConsumption panoramicRealTimeConsumption) {
    	realTimeConsumptionService.save(panoramicRealTimeConsumption);
        return ResultCode.getSuccessReturn(panoramicRealTimeConsumption);
    }
    @ApiOperation(value = "消耗数据定时任务汇总", notes = "消耗数据定时任务汇总")
    @PostMapping("/task")
    public ResultCode<Void> task() {
    	DB_LOGGER.warn("<--消耗数据定时任务汇总  开始-->");
    	realTimeConsumptionService.realTimeConsumptionSummaryTask();
    	DB_LOGGER.warn("<--消耗数据定时任务汇总  结束-->");
        return ResultCode.SUCCESS;
    }

    @DeleteMapping("/{id}")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicRealTimeConsumption> update(PanoramicRealTimeConsumption panoramicRealTimeConsumption) {
    	realTimeConsumptionService.update(panoramicRealTimeConsumption);
        return ResultCode.getSuccessReturn(panoramicRealTimeConsumption);
    }
    @GetMapping("/{id}")
    public ResultCode<PanoramicRealTimeConsumption> detail(@PathVariable Integer id) {
        PanoramicRealTimeConsumption panoramicRealTimeConsumption = realTimeConsumptionService.findById(id);
        return ResultCode.getSuccessReturn(panoramicRealTimeConsumption);
    }

//    @GetMapping
//    public ResultCode<PageInfo> list(Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        List<PanoramicRealTimeConsumption> list = realTimeConsumptionService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultCode.getSuccessReturn(pageInfo);
//    }
}
