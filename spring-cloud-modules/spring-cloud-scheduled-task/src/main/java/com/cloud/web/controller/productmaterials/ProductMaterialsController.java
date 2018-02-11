package com.cloud.web.controller.productmaterials;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author summer 2017/11/21.
 */
@Api
@RestController
@RequestMapping("/product/materials")
public class ProductMaterialsController {

	// @DeleteMapping("/{id}")
	// public ResultCode<PanoramicProductMaterials> delete(@PathVariable("id")
	// Integer id) {
	// return ResultCode.SUCCESS;
	// }
	//
	// @PutMapping
	// public ResultCode<PanoramicProductMaterials> update(PanoramicProductMaterials
	// panoramicProductMaterials) {
	// productMaterialsService.update(panoramicProductMaterials);
	// return ResultCode.getSuccessReturn(panoramicProductMaterials);
	// }
	//
	// @GetMapping("/{id}")
	// public ResultCode<PanoramicProductMaterials> detail(@PathVariable("id")
	// Integer id) {
	// PanoramicProductMaterials panoramicProductMaterials =
	// productMaterialsService.findById(id);
	// return ResultCode.getSuccessReturn(panoramicProductMaterials);
	// }

	// @GetMapping
	// public ResultCode<PageInfo> list(Integer page, Integer size) {
	// PageHelper.startPage(page, size);
	// List<PanoramicProductMaterials> list = productMaterialsService.findAll();
	// PageInfo pageInfo = new PageInfo(list);
	// return ResultCode.getSuccessReturn(pageInfo);
	// }
}
