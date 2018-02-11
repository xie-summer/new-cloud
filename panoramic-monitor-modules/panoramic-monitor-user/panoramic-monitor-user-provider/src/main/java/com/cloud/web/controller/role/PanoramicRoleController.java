package com.cloud.web.controller.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panoramic.user.acl.PanoramicRoleService;

/**
 * @author sunmer on 2017/11/06.
 */
@RestController
@RequestMapping("/role")
public class PanoramicRoleController {
	@Autowired
	@Qualifier("roleService")
	private PanoramicRoleService roleService;

	// @PostMapping
	// public ResultCode<PanoramicRole> add(PanoramicRole panoramicRole) {
	// roleService.save(panoramicRole);
	// return ResultCode.getSuccessReturn(panoramicRole);
	// }
	//
	// @DeleteMapping("/{id}")
	// public ResultCode<PanoramicRole> delete(@PathVariable Integer id) {
	// roleService.deleteById(id);
	// return ResultCode.getSuccessMap();
	// }
	//
	// @PutMapping
	// public ResultCode<PanoramicRole> update(PanoramicRole panoramicRole) {
	// roleService.update(panoramicRole);
	// return ResultCode.getSuccessReturn(panoramicRole);
	// }
	//
	// @GetMapping("/{id}")
	// public ResultCode<PanoramicRole> detail(@PathVariable Integer id) {
	// PanoramicRole panoramicRole = roleService.findById(id);
	// return ResultCode.getSuccessReturn(panoramicRole);
	// }
	//
	// @GetMapping
	// public ResultCode<PageInfo> list(Integer page, Integer size) {
	// PageHelper.startPage(page, size);
	// List<PanoramicRole> list = roleService.findAll();
	// PageInfo pageInfo = new PageInfo(list);
	// return ResultCode.getSuccessReturn(pageInfo);
	// }
}
