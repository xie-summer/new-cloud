package com.monitor.web.controller.sparepartsintoinventory;

import io.swagger.annotations.ApiOperation;

import com.monitor.api.sparepartsintoinventory.PanoramicSparePartsIntoInventoryService;
import com.monitor.dto.sparepartsintoinventory.PanoramicSparePartsIntoInventoryDto;
import com.monitor.model.sparepartsintoinventory.PanoramicSparePartsIntoInventory;
import com.cloud.api.vo.ResultCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
* @author gang
* 2017/12/26.
*/
@RestController
@RequestMapping("/panoramic/spare/parts/into/inventory")
public class PanoramicSparePartsIntoInventoryController {
    @Autowired
    @Qualifier("sparePartsIntoInventoryService")
    private PanoramicSparePartsIntoInventoryService panoramicSparePartsIntoInventoryService;

    @PostMapping
    public ResultCode<PanoramicSparePartsIntoInventory> add(PanoramicSparePartsIntoInventory panoramicSparePartsIntoInventory) {
        panoramicSparePartsIntoInventoryService.save(panoramicSparePartsIntoInventory);
        return ResultCode.getSuccessReturn(panoramicSparePartsIntoInventory);
    }

    @DeleteMapping("/{id}")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicSparePartsIntoInventory> update(PanoramicSparePartsIntoInventory panoramicSparePartsIntoInventory) {
        panoramicSparePartsIntoInventoryService.update(panoramicSparePartsIntoInventory);
        return ResultCode.getSuccessReturn(panoramicSparePartsIntoInventory);
    }
    
    @GetMapping("/{id}")
    public ResultCode<PanoramicSparePartsIntoInventory> detail(@PathVariable Integer id) {
        PanoramicSparePartsIntoInventory panoramicSparePartsIntoInventory = panoramicSparePartsIntoInventoryService.findById(id);
        return ResultCode.getSuccessReturn(panoramicSparePartsIntoInventory);
    }

    @GetMapping
    public ResultCode<List<PanoramicSparePartsIntoInventory>> list(Integer page, Integer size) {
        List<PanoramicSparePartsIntoInventory> list = panoramicSparePartsIntoInventoryService.findAll();
        return ResultCode.getSuccessReturn(list);
    }
    
    @ApiOperation(value = "周单位备品备件入库内容查询", notes = "根据时间查询本周的最多备品备件入库货值")
    @GetMapping("/weeklysummary/in/{date}")
    public ResultCode<List<PanoramicSparePartsIntoInventoryDto>> listWeeklyInSummary(@PathVariable String date) {
        List<PanoramicSparePartsIntoInventoryDto> list = panoramicSparePartsIntoInventoryService.findWeeklyInSummary(date);
        return ResultCode.getSuccessReturn(list);
    }
    
    @ApiOperation(value = "周单位备品备件出库内容查询", notes = "根据时间查询本周的最多备品备件出库货值")
    @GetMapping("/weeklysummary/out/{date}")
    public ResultCode<List<PanoramicSparePartsIntoInventoryDto>> listWeeklyOutSummary(@PathVariable String date) {
        List<PanoramicSparePartsIntoInventoryDto> list = panoramicSparePartsIntoInventoryService.findWeeklyOutSummary(date);
        return ResultCode.getSuccessReturn(list);
    }
    
    @ApiOperation(value = "月单位消耗货值排行内容查询", notes = "根据时间查询本月的最多备品备件消耗货值")
    @GetMapping("/monthlyconsume/price/{date}")
    public ResultCode<List<PanoramicSparePartsIntoInventoryDto>> listMonthlyConsumePrice(@PathVariable String date) {
        List<PanoramicSparePartsIntoInventoryDto> list = panoramicSparePartsIntoInventoryService.findMonthlyMaxPrice(date);
        return ResultCode.getSuccessReturn(list);
    }
    
    @ApiOperation(value = "月单位消耗量排行内容查询", notes = "根据时间查询本月的最多备品备件消耗量")
    @GetMapping("/monthlyconsume/value/{date}")
    public ResultCode<List<PanoramicSparePartsIntoInventoryDto>> listMonthlyConsumeValue(@PathVariable String date) {
        List<PanoramicSparePartsIntoInventoryDto> list = panoramicSparePartsIntoInventoryService.findMonthlyMaxValue(date);
        return ResultCode.getSuccessReturn(list);
    }
    
    @ApiOperation(value = "当日备品备件入出库量", notes = "仓库监管-备品备件库 当日入出库量")
    @GetMapping("/{date}/{type}/{page}/{size}")
	public ResultCode<PageInfo<PanoramicSparePartsIntoInventory>> listDayInventory(@PathVariable("date") String date,
			@PathVariable("type") String type,
			@PathVariable("page") Integer page,
			@PathVariable("size") Integer size) {
    		PageHelper.startPage(page, size);
		List<PanoramicSparePartsIntoInventory> list = panoramicSparePartsIntoInventoryService.listDayInventory(date,
				type);
		PageInfo<PanoramicSparePartsIntoInventory> pageList = new PageInfo<>(list);
		return ResultCode.getSuccessReturn(pageList);
	}
}
