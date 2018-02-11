package com.monitor.web.controller.productionefficiency;

import com.cloud.api.vo.ResultCode;
import com.monitor.api.productionefficiency.PanoramicProductionEfficiencyService;
import com.monitor.model.productionefficiency.PanoramicProductionEfficiency;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * 2017/11/21
 */
@Api
@RestController
@RequestMapping("/production/efficiency")
public class PanoramicProductionEfficiencyController {
    @Autowired
    @Qualifier("productionEfficiencyService")
    private PanoramicProductionEfficiencyService productionEfficiencyService;

    @PostMapping("/{id}")
    public ResultCode<PanoramicProductionEfficiency> add(PanoramicProductionEfficiency panoramicProductionEfficiency) {
        productionEfficiencyService.save(panoramicProductionEfficiency);
        return ResultCode.getSuccessReturn(panoramicProductionEfficiency);
    }

    @DeleteMapping("/{id}")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicProductionEfficiency> update(PanoramicProductionEfficiency panoramicProductionEfficiency) {
        productionEfficiencyService.update(panoramicProductionEfficiency);
        return ResultCode.getSuccessReturn(panoramicProductionEfficiency);
    }

    @GetMapping("/{id}")
    public ResultCode<PanoramicProductionEfficiency> detail(@PathVariable Integer id) {
        PanoramicProductionEfficiency panoramicProductionEfficiency = productionEfficiencyService.findById(id);
        return ResultCode.getSuccessReturn(panoramicProductionEfficiency);
    }

//    @GetMapping
//    public ResultCode<PageInfo> list(Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        List<PanoramicProductionEfficiency> list = productionEfficiencyService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultCode.getSuccessReturn(pageInfo);
//    }
}
