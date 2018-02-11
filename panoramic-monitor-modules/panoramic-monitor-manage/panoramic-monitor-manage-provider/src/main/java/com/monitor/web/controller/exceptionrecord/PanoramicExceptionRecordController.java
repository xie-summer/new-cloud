package com.monitor.web.controller.exceptionrecord;

import com.cloud.api.vo.ResultCode;
import com.cloud.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.api.exceptionrecord.PanoramicExceptionRecordService;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;
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
@RequestMapping("/exception/record")
public class PanoramicExceptionRecordController extends AbstractAnnotationController {
    @Autowired
    @Qualifier("exceptionRecordService")
    private PanoramicExceptionRecordService exceptionRecordService;

    @ApiOperation(value = "异常信息接口-获取异常详情", notes = "获取异常详情")
    @GetMapping("/{id}")
    public ResultCode<PanoramicExceptionRecord> detail(@PathVariable Integer id) {
        PanoramicExceptionRecord panoramicExceptionRecord = exceptionRecordService.findById(id);
        return ResultCode.getSuccessReturn(panoramicExceptionRecord);
    }
    @ApiOperation(value = "异常信息接口-保存异常信息接口", notes = "保存异常信息，{category,必填;alarmContent,必填}")
    @PostMapping
    public ResultCode<PanoramicExceptionRecord> add(PanoramicExceptionRecord exceptionRecord) {
        exceptionRecordService.save(exceptionRecord);
        return ResultCode.SUCCESS;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "异常信修改接口", notes = "更新异常信息状态，即删除异常信息")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        PanoramicExceptionRecord record = exceptionRecordService.findById(id);
        record.setDeleteFlag(0);
        record.setUtime(DateUtil.getCurFullTimestamp());
        record.setDtime(record.getUtime());
        exceptionRecordService.update(record);
        return ResultCode.SUCCESS;
    }

    @PutMapping
    @ApiOperation(value = "异常信息更新接口", notes = "更新异常信息状态，即更新异常信息状态")
    public ResultCode<PanoramicExceptionRecord> update(PanoramicExceptionRecord record) {
        record.setStatus(1);
        record.setUtime(DateUtil.getCurFullTimestamp());
        exceptionRecordService.update(record);
        return ResultCode.SUCCESS;
    }

    /**
     * @param startDate
     * @param endDate
     * @param page
     * @param size
     * @return
     */
    //
    @ApiOperation(value = "异常信息查询接口", notes = "分页查询所有异常信息")
    @GetMapping("/{page}/{size}/{startDate}/{endDate}")
    public ResultCode<PageInfo<PanoramicExceptionRecord>> list(@PathVariable Integer page, @PathVariable Integer size, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        PageHelper.startPage(page, size);
        List<PanoramicExceptionRecord> list = exceptionRecordService.listByDate(startDate, endDate);
        PageInfo<PanoramicExceptionRecord> pageInfo = new PageInfo<>(list);
        return ResultCode.getSuccessReturn(pageInfo);
    }

    @ApiOperation(value = "异常信息查询接口", notes = "根据指定分类分页查询异常信息")
    @GetMapping("/{category}/{page}/{size}/{startDate}/{endDate}")
    public ResultCode<PageInfo<PanoramicExceptionRecord>> listByCategory(@PathVariable("category") String category, @PathVariable("page") Integer page, @PathVariable("size") Integer size,
                                                                         @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        PageHelper.startPage(page, size);
        List<PanoramicExceptionRecord> list = exceptionRecordService.listByCategory(category, startDate, endDate);
        PageInfo<PanoramicExceptionRecord> pageInfo = new PageInfo<>(list);
        return ResultCode.getSuccessReturn(pageInfo);
    }
}
