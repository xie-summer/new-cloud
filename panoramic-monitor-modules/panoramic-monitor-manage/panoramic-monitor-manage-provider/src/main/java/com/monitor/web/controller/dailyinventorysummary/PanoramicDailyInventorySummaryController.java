package com.monitor.web.controller.dailyinventorysummary;

import com.cloud.api.vo.ResultCode;
import com.cloud.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.api.dailyinventorysummary.PanoramicDailyInventorySummaryService;
import com.monitor.constant.DailyInventorySummaryCodeConstant;
import com.monitor.model.dailyinventorysummary.PanoramicDailyInventorySummary;
import com.monitor.web.controller.base.AbstractAnnotationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author summer 2017/11/21.
 */
@Api
@RestController
@RequestMapping("/daily/inventory/summary")
public class PanoramicDailyInventorySummaryController extends AbstractAnnotationController {
    @Autowired
    @Qualifier("dailyInventorySummaryService")
    private PanoramicDailyInventorySummaryService dailyInventorySummaryService;

    /**
     * 根据时间和物料编码查询库存
     *
     * @param code
     * @param date
     * @return
     */
    @ApiOperation(value = "库存查询接口", notes = "根据时间和物料编码查询库存")
    @GetMapping("/{date}/{code}")
    public ResultCode<PanoramicDailyInventorySummary> queryByDateAndCode(@PathVariable String date,
                                                                         @PathVariable String code) {
        PanoramicDailyInventorySummary dailyInventorySummary = dailyInventorySummaryService
                .queryByDateAndCode(code, date);
        return ResultCode.getSuccessReturn(dailyInventorySummary);
    }

    /**
     * 根据时间和物料编码查询所有库存
     *
     * @param date
     * @return
     */
    @ApiOperation(value = "所有库存查询接口", notes = "根据时间查询所有库存")
    @GetMapping("/list/{date}")
    public ResultCode<List<PanoramicDailyInventorySummary>> listByDateAndCode(@PathVariable String date
    ) {
        List<PanoramicDailyInventorySummary> summaryList = dailyInventorySummaryService
                .listByDateAndCode(date);
        return ResultCode.getSuccessReturn(summaryList);
    }

    @ApiOperation(value = "库存数据校验数据接口", notes = "库存数据校验")
    @GetMapping("/check/{date}")
    public ResultCode<Map<String, String>> check(@RequestParam("codeList[]") List<String> codeList, @PathVariable("date") String date) {
        if (null == codeList || codeList.size() == 0) {
            return ResultCode.getFailure("数据格式错误！");
        }

        Map<String, String> result = dailyInventorySummaryService.check(codeList, date);
        return ResultCode.getSuccessReturn(result);
    }
    @ApiOperation(value = "每日库存数据定时任务汇总", notes = "每日库存数据定时任务汇总")
    @PostMapping("/task")
    public ResultCode<Void> task() {
    	DB_LOGGER.warn("<--每日库存数据定时任务汇总  开始-->");
        String date = DateUtil.getCurFullTimestampStr();
        List<PanoramicDailyInventorySummary> records = dailyInventorySummaryService
                .listByDateAndCode(DateUtil.getYestoryDate());
        dailyInventorySummaryService.dailyInventorySummaryTask(date, records);
        DB_LOGGER.warn("<--每日库存数据定时任务汇总  结束-->");
        return ResultCode.SUCCESS;
    }

    @DeleteMapping("/{id}")
    public ResultCode<PanoramicDailyInventorySummary> delete(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }

    @PutMapping
    public ResultCode<PanoramicDailyInventorySummary> update(
            PanoramicDailyInventorySummary panoramicDailyInventorySummary) {
        dailyInventorySummaryService.update(panoramicDailyInventorySummary);
        return ResultCode.getSuccessReturn(panoramicDailyInventorySummary);
    }

    @GetMapping("/{id}")
    public ResultCode<PanoramicDailyInventorySummary> detail(@PathVariable Integer id) {
        PanoramicDailyInventorySummary panoramicDailyInventorySummary = dailyInventorySummaryService
                .findById(id);
        return ResultCode.getSuccessReturn(panoramicDailyInventorySummary);
    }

    @GetMapping("/{date}/{page}/{size}")
    public ResultCode<PageInfo<PanoramicDailyInventorySummary>> list(@PathVariable String date,
                                                                     @PathVariable Integer page, @PathVariable Integer size) {
        PageHelper.startPage(page, size);
        List<PanoramicDailyInventorySummary> list = dailyInventorySummaryService.listByDateAndCode(date, DailyInventorySummaryCodeConstant.CODE_LIST);
        PageInfo<PanoramicDailyInventorySummary> pageInfo = new PageInfo<>(list);
        return ResultCode.getSuccessReturn(pageInfo);
    }
}
