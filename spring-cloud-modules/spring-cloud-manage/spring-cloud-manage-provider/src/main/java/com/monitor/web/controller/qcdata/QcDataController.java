package com.monitor.web.controller.qcdata;

import com.cloud.api.vo.ResultCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.api.qcdata.QcDataService;
import com.monitor.model.qcdata.QcData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author summer
 * 2017/11/27
 */
@Api
@RestController
@RequestMapping("/qc/data")
public class QcDataController {
    @Autowired
    @Qualifier("qcDataService")
    private QcDataService qcDataService;

    @PostMapping("/{id}")
    public ResultCode<QcData> add(QcData qcData) {
        qcDataService.save(qcData);
        return ResultCode.getSuccessReturn(qcData);
    }

    @DeleteMapping("/{id}")
    public ResultCode<Void> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<QcData> update(QcData qcData) {
        qcDataService.update(qcData);
        return ResultCode.getSuccessReturn(qcData);
    }

    @GetMapping("/{id}")
    public ResultCode<QcData> detail(@PathVariable Integer id) {
        QcData qcData = qcDataService.findById(id);
        return ResultCode.getSuccessReturn(qcData);
    }

    @ApiOperation(value = "质检合格率查询接口", notes = "根据时间和类型查询质检合格率")
    @GetMapping("/{date}/{type}")
    public ResultCode<Double> passRate(@PathVariable String date, @PathVariable String type) {
        Double passRate = qcDataService.passRate(date, type);
        return ResultCode.getSuccessReturn(passRate);
    }

    @ApiOperation(value = "分页查询质检详情查询接口", notes = "根据时间分页查询质检详情数据列表")
    @GetMapping("/{date}/{type}/{page}/{size}")
    public ResultCode<PageInfo<List<QcData>>> list(@PathVariable String date, @PathVariable String type, @PathVariable Integer page, @PathVariable Integer size) {
        PageHelper.startPage(page, size);
        List<QcData> list = qcDataService.listByDate(date, type);
        PageInfo<List<QcData>> pageInfo = new PageInfo(list);
        return ResultCode.getSuccessReturn(pageInfo);
    }
}
