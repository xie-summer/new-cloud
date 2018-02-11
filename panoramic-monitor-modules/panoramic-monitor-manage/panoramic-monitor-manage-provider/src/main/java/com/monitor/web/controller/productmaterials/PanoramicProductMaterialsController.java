package com.monitor.web.controller.productmaterials;

import com.cloud.api.vo.ResultCode;
import com.monitor.api.productmaterials.PanoramicProductMaterialsService;
import com.monitor.dto.productmaterials.PanoramicProductMaterialsDto;
import com.monitor.model.productmaterials.PanoramicProductMaterials;
import com.monitor.web.controller.base.AbstractAnnotationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * 2017/11/21.
 */
@Api
@RestController
@RequestMapping("/product/materials")
public class PanoramicProductMaterialsController extends AbstractAnnotationController {
    @Autowired
    @Qualifier("productMaterialsService")
    private PanoramicProductMaterialsService productMaterialsService;

    @PostMapping
    public ResultCode<PanoramicProductMaterials> add(PanoramicProductMaterials panoramicProductMaterials) {
        productMaterialsService.save(panoramicProductMaterials);
        return ResultCode.getSuccessReturn(panoramicProductMaterials);
    }
    @ApiOperation(value = "产品定时任务汇总", notes = "产品定时任务汇总")
    @PostMapping("/task")
    public ResultCode<PanoramicProductMaterials> task() {
    	DB_LOGGER.warn("<--产品定时任务汇总  开始-->");
    	productMaterialsService.realTimeProductSummaryTask();
        DB_LOGGER.warn("<--产品定时任务汇总  结束-->");
        return ResultCode.SUCCESS;
    }

    @DeleteMapping("/{id}")
    public ResultCode<PanoramicProductMaterials> delete(@PathVariable("id") Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicProductMaterials> update(PanoramicProductMaterials panoramicProductMaterials) {
        productMaterialsService.update(panoramicProductMaterials);
        return ResultCode.getSuccessReturn(panoramicProductMaterials);
    }

    @GetMapping("/{id}")
    public ResultCode<PanoramicProductMaterials> detail(@PathVariable("id") Integer id) {
        PanoramicProductMaterials panoramicProductMaterials = productMaterialsService.findById(id);
        return ResultCode.getSuccessReturn(panoramicProductMaterials);
    }
    
//    @GetMapping
//    public ResultCode<PageInfo> list(Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        List<PanoramicProductMaterials> list = productMaterialsService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultCode.getSuccessReturn(pageInfo);
//    }
}
