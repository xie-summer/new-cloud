package com.monitor.web.controller.rawmaterials;

import com.cloud.api.vo.ResultCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.api.rawmaterials.PanoramicRawMaterialsService;
import com.monitor.model.rawmaterials.PanoramicRawMaterials;
import com.monitor.web.controller.base.AbstractAnnotationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author summer
 * 2017/11/21.
 */
@Api
@RestController
@RequestMapping("/raw/materials")
public class PanoramicRawMaterialsController extends AbstractAnnotationController {
    @Autowired
    @Qualifier("rawMaterialsService")
    private PanoramicRawMaterialsService rawMaterialsService;

    @PostMapping
    public ResultCode<PanoramicRawMaterials> add(PanoramicRawMaterials panoramicRawMaterials) {
        rawMaterialsService.save(panoramicRawMaterials);
        return ResultCode.getSuccessReturn(panoramicRawMaterials);
    }

    @ApiOperation(value = "库存可使用天数-查询接口", notes = "根据时间和code查询库存可使用天数数据")
    @GetMapping("/{code}/{date}")
    public ResultCode<Integer> number(@PathVariable("code") String code, @PathVariable("date") String date) {
        Integer count = rawMaterialsService.countUsable(code, date);
        return ResultCode.getSuccessReturn(count);
    }

    @DeleteMapping("/{id}")
    public ResultCode<PanoramicRawMaterials> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicRawMaterials> update(PanoramicRawMaterials panoramicRawMaterials) {
        rawMaterialsService.update(panoramicRawMaterials);
        return ResultCode.getSuccessReturn(panoramicRawMaterials);
    }

    @GetMapping("/{id}")
    public ResultCode<PanoramicRawMaterials> detail(@PathVariable Integer id) {
        PanoramicRawMaterials panoramicRawMaterials = rawMaterialsService.findById(id);
        return ResultCode.getSuccessReturn(panoramicRawMaterials);
    }

    @GetMapping("/{date}/{page}/{size}")
    public ResultCode<PageInfo> list(@PathVariable("date") String date, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageHelper.startPage(page, size);
        List<PanoramicRawMaterials> list = rawMaterialsService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultCode.getSuccessReturn(pageInfo);
    }
}
