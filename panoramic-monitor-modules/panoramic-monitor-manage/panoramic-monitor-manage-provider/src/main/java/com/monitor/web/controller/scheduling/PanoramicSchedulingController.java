package com.monitor.web.controller.scheduling;

import com.cloud.api.vo.ResultCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.api.scheduling.PanoramicSchedulingService;
import com.monitor.model.scheduling.PanoramicScheduling;
import com.monitor.web.controller.base.AbstractAnnotationController;
import io.swagger.annotations.Api;
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
@RequestMapping("/scheduling")
public class PanoramicSchedulingController extends AbstractAnnotationController {
    @Autowired
    @Qualifier("schedulingService")
    private PanoramicSchedulingService schedulingService;

    @PostMapping
    public ResultCode<PanoramicScheduling> add(PanoramicScheduling panoramicScheduling) {
        schedulingService.save(panoramicScheduling);
        return ResultCode.getSuccessReturn(panoramicScheduling);
    }

    @DeleteMapping("/{id}")
    public ResultCode<PanoramicScheduling> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicScheduling> update(PanoramicScheduling panoramicScheduling) {
        schedulingService.update(panoramicScheduling);
        return ResultCode.getSuccessReturn(panoramicScheduling);
    }

    @GetMapping("/{id}")
    public ResultCode<PanoramicScheduling> detail(@PathVariable Integer id) {
        PanoramicScheduling panoramicScheduling = schedulingService.findById(id);
        return ResultCode.getSuccessReturn(panoramicScheduling);
    }

//    @GetMapping
//    public ResultCode<PageInfo> list(Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        List<PanoramicScheduling> list = schedulingService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultCode.getSuccessReturn(pageInfo);
//    }
}
