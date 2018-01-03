package com.monitor.web.controller.materialintoinventory;

import com.cloud.api.vo.ResultCode;
import com.monitor.model.materialintoinventory.PanoramicMaterialIntoInventory;
import com.monitor.api.materialintoinventory.PanoramicMaterialIntoInventoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.monitor.web.controller.base.AbstractAnnotationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author gang
* 2017/12/27.
*/
@RestController
@RequestMapping("/panoramic/material/into/inventory")
public class PanoramicMaterialIntoInventoryController extends AbstractAnnotationController{
   @Autowired
   @Qualifier("materialIntoInventoryService")
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
}
