package com.monitor.web.controller.materialintoinventory;

import com.cloud.api.vo.ResultCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.model.materialintoinventory.PanoramicMaterialIntoInventory;
import io.swagger.annotations.ApiOperation;
import com.monitor.api.materialintoinventory.PanoramicMaterialIntoInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
* @author gang
* 2017/12/27.
*/
@RestController
@RequestMapping("/material/into/inventory")
public class PanoramicMaterialIntoInventoryController{
   @Autowired
    private PanoramicMaterialIntoInventoryService panoramicMaterialIntoInventoryService;

    @PostMapping
    public ResultCode<PanoramicMaterialIntoInventory> add(PanoramicMaterialIntoInventory panoramicMaterialIntoInventory) {
        panoramicMaterialIntoInventoryService.save(panoramicMaterialIntoInventory);
        return ResultCode.getSuccessReturn(panoramicMaterialIntoInventory);
    }

    @DeleteMapping("/{id}")
    public ResultCode<PanoramicMaterialIntoInventory> delete(@PathVariable Integer id) {
    		return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicMaterialIntoInventory> update(PanoramicMaterialIntoInventory panoramicMaterialIntoInventory) {
        panoramicMaterialIntoInventoryService.update(panoramicMaterialIntoInventory);
        return ResultCode.getSuccessReturn(panoramicMaterialIntoInventory);
    }
    @GetMapping("/{id}")
    public ResultCode<PanoramicMaterialIntoInventory> detail(@PathVariable Integer id) {
        PanoramicMaterialIntoInventory panoramicMaterialIntoInventory = panoramicMaterialIntoInventoryService.findById(id);
        return ResultCode.getSuccessReturn(panoramicMaterialIntoInventory);
    }

    @GetMapping
    public ResultCode<List<PanoramicMaterialIntoInventory>> list(Integer size) {
        List<PanoramicMaterialIntoInventory> list = panoramicMaterialIntoInventoryService.findAll();
        return ResultCode.getSuccessReturn(list);
    }
    
    @ApiOperation(value = "当日出入库查询", notes = "指定代码，日期，出入库信息查询出入库信息")
	@GetMapping("/{code}/{type}/{date}/{page}/{size}")
	public ResultCode<PageInfo<PanoramicMaterialIntoInventory>> findMaterialValue(
					@PathVariable("code") String code,
					@PathVariable("type") String type,
					@PathVariable("date") String date,
					@PathVariable("page") Integer page,
					@PathVariable("size") Integer size) {
    		PageHelper.startPage(page, size);
    		List<PanoramicMaterialIntoInventory> result = 
        		panoramicMaterialIntoInventoryService.findMaterialValue(code,type,date);
        PageInfo<PanoramicMaterialIntoInventory> pageInfo = new PageInfo<>(result);
        return ResultCode.getSuccessReturn(pageInfo);
    } 
    
    @ApiOperation(value = "当日入出库总量以及最新更新时间", notes = "仓库监管-原料库 当日入出库")
    @GetMapping("/summary/{code}/{type}/{date}")
    public ResultCode<PanoramicMaterialIntoInventory> findSummaryByDate(
    					@PathVariable("code") String code,
    					@PathVariable("type") String type,
    					@PathVariable("date") String date) {
        PanoramicMaterialIntoInventory panoramicMaterialIntoInventory = panoramicMaterialIntoInventoryService.findSummaryByDate(code,type,date);
        return ResultCode.getSuccessReturn(panoramicMaterialIntoInventory);
    }
}
