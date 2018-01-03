package com.monitor.web.controller.realtimeconsumptiongather;

import com.cloud.api.vo.ResultCode;
import com.monitor.api.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherService;
import com.monitor.dto.realtimeconsumptiongather.PanoramicRealTimeConsumptionGatherDto;
import com.monitor.model.realtimeconsumptiongather.PanoramicRealTimeConsumptionGather;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author summer 2017/11/21.
 */
@Api
@RestController
@RequestMapping("/real/time/consumption/gather")
public class PanoramicRealTimeConsumptionGatherController {
    @Autowired
    @Qualifier("realTimeConsumptionGatherService")
    private PanoramicRealTimeConsumptionGatherService realTimeConsumptionGatherService;

    @ApiOperation(value = "实时消耗汇总查询接口", notes = "根据时间和code查询实时消耗汇总数据")
    @GetMapping("/{date}/{code}")
    public ResultCode<List<PanoramicRealTimeConsumptionGather>> listByCodeAndDate(@PathVariable String date,
                                                                                  @PathVariable String code) {
        List<PanoramicRealTimeConsumptionGather> list = realTimeConsumptionGatherService.listByCodeAndDate(date, code);
        return ResultCode.getSuccessReturn(list);
    }

    @ApiOperation(value = "月度实时消耗汇总查询接口", notes = "根据时间和code查询月度实时消耗汇总数据(月累计消耗量)")
    @GetMapping("/monthly/statistics/{date}/{code}")
    public ResultCode<PanoramicRealTimeConsumptionGather> queryMonthlyStatisticsByDate(@PathVariable String date,
                                                                                      @PathVariable String code) {
        PanoramicRealTimeConsumptionGather gather = realTimeConsumptionGatherService.queryMonthlyStatisticsByDate(date, code);
        return ResultCode.getSuccessReturn(gather);
    }

    @ApiOperation(value = "数据校验-磷矿查询接口", notes = "根据时间和code查询每日数据校验查询数据(每日累计消耗量和记录值)")
    @GetMapping("/day/statistics/{date}/{code}")
    public ResultCode<PanoramicRealTimeConsumptionGatherDto> listDayStatisticsByDate(@PathVariable String date,
                                                                                     @PathVariable String code) {
        PanoramicRealTimeConsumptionGatherDto gather = realTimeConsumptionGatherService.queryDayStatisticsByDate(date, code);
        return ResultCode.getSuccessReturn(gather);
    }

    @PostMapping
    public ResultCode<PanoramicRealTimeConsumptionGather> add(
            PanoramicRealTimeConsumptionGather panoramicRealTimeConsumptionGather) {
        realTimeConsumptionGatherService.save(panoramicRealTimeConsumptionGather);
        return ResultCode.getSuccessReturn(panoramicRealTimeConsumptionGather);
    }

    @DeleteMapping("/{id}")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicRealTimeConsumptionGather> update(
            PanoramicRealTimeConsumptionGather panoramicRealTimeConsumptionGather) {
        realTimeConsumptionGatherService.update(panoramicRealTimeConsumptionGather);
        return ResultCode.getSuccessReturn(panoramicRealTimeConsumptionGather);
    }

    @GetMapping("/{id}")
    public ResultCode<PanoramicRealTimeConsumptionGather> detail(@PathVariable Integer id) {
        PanoramicRealTimeConsumptionGather panoramicRealTimeConsumptionGather = realTimeConsumptionGatherService
                .findById(id);
        return ResultCode.getSuccessReturn(panoramicRealTimeConsumptionGather);
    }

    // @GetMapping
    // public ResultCode<PageInfo> list(Integer page, Integer size) {
    // PageHelper.startPage(page, size);
    // List<PanoramicRealTimeConsumptionGather> list =
    // realTimeConsumptionGatherService.findAll();
    // PageInfo pageInfo = new PageInfo(list);
    // return ResultCode.getSuccessReturn(pageInfo);
    // }
}
