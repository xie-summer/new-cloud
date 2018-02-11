package com.monitor.web.controller.inventoryentry;

import com.cloud.api.vo.ResultCode;
import com.cloud.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monitor.api.inventoryentry.PanoramicInventoryEntryService;
import com.monitor.model.exceptionrecord.PanoramicExceptionRecord;
import com.monitor.model.inventoryentry.PanoramicInventoryEntry;
import com.monitor.web.controller.base.AbstractAnnotationController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author summer 2017/11/30
 */
@Api
@RestController
@RequestMapping("/panoramic/inventory/entry")
public class PanoramicInventoryEntryController extends AbstractAnnotationController {
	@Autowired
	@Qualifier("inventoryEntryService")
	private PanoramicInventoryEntryService inventoryEntryService;

	@ApiOperation(value = "人工录入保存数据接口", notes = "人工录入数据，按照排班分别保存对应数据")
	@PostMapping
	public ResultCode<Void> add(@RequestBody List<PanoramicInventoryEntry> inventoryEntryList) {
		if (null == inventoryEntryList || inventoryEntryList.size() == 0) {
			return ResultCode.getFailure("上传数据格式错误！");
		}
		inventoryEntryService.saveOrUpdate(inventoryEntryList);
		return ResultCode.SUCCESS;
	}

	@DeleteMapping("/{id}")
	public ResultCode<Void> delete(@PathVariable Integer id) {
		return ResultCode.SUCCESS;
	}
	@ApiOperation(value = "扫描人工录入数据定时任务", notes = "扫描人工录入数据定时任务")
	@PostMapping("/task")
	public ResultCode<Void> task() {
		DB_LOGGER.warn("<--扫描人工录入数据定时任务汇总  开始-->");
		String date = DateUtil.getYestoryDate();
		inventoryEntryService.manualEntryExceptionRecordTask(date);
		DB_LOGGER.warn("<--扫描人工录入数据定时任务汇总  结束-->");
		return ResultCode.SUCCESS;
	}

	@ApiOperation(value = "人工录入数据查询数据接口", notes = "人工录入数据，按照时间查询对应数据，用于数据回显")
	@GetMapping("/{date}")
	public ResultCode<List<PanoramicInventoryEntry>> findByDate(@PathVariable("date") String date) {
		List<PanoramicInventoryEntry> records = inventoryEntryService.findByDate(date);
		return ResultCode.getSuccessReturn(records);
	}

	@ApiOperation(value = "人工录入数据查询数据接口", notes = "人工录入数据模块未录入异常查询，按照时间查询对应数据，返回对应30天内未录入数据记录")
	@GetMapping("/msg/{date}")
	public ResultCode<List<PanoramicExceptionRecord>> findMsgByDate(@PathVariable("date") String date) {
		List<PanoramicExceptionRecord> records = inventoryEntryService.findMsgByDate(date);
		return ResultCode.getSuccessReturn(records);
	}

	@GetMapping("/{date}/{page}/{size}")
	public ResultCode<PageInfo> list(@PathVariable("date") String date, @PathVariable("page") Integer page,
			@PathVariable("size") Integer size) {
		PageHelper.startPage(page, size);
		List<PanoramicInventoryEntry> list = inventoryEntryService.findAll();
		PageInfo pageInfo = new PageInfo(list);
		return ResultCode.getSuccessReturn(pageInfo);
	}
}
