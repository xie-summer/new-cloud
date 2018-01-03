package com.cloud.web.controller.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panoramic.user.acl.PanoramicPermissionService;

/**
 * @author summer 2017/11/08.
 */
@RestController
@RequestMapping("/permission")
public class PanoramicPermissionController {
	@Autowired
	@Qualifier("permissionService")
	private PanoramicPermissionService permissionService;

	// @PostMapping
	// public ResultCode<PanoramicPermission> add(PanoramicPermission
	// panoramicPermission) {
	// panoramicPermissionService.save(panoramicPermission);
	// return ResultCode.getSuccessReturn(panoramicPermission);
	// }
	//
	// @DeleteMapping("/{id}")
	// public ResultCode<PanoramicPermission> delete(@PathVariable Integer id) {
	// panoramicPermissionService.deleteById(id);
	// return ResultCode.getSuccessMap();
	// }
	//
	// @PutMapping
	// public ResultCode<PanoramicPermission> update(PanoramicPermission
	// panoramicPermission) {
	// panoramicPermissionService.update(panoramicPermission);
	// return ResultCode.getSuccessReturn(panoramicPermission);
	// }
	//
	// @GetMapping("/{id}")
	// public ResultCode<PanoramicPermission> detail(@PathVariable Integer id) {
	// PanoramicPermission panoramicPermission =
	// panoramicPermissionService.findById(id);
	// return ResultCode.getSuccessReturn(panoramicPermission);
	// }
	//
	// @GetMapping
	// public ResultCode<PageInfo> list(Integer page, Integer size) {
	// PageHelper.startPage(page, size);
	// List<PanoramicPermission> list = panoramicPermissionService.findAll();
	// PageInfo pageInfo = new PageInfo(list);
	// return ResultCode.getSuccessReturn(pageInfo);
	// }
}
