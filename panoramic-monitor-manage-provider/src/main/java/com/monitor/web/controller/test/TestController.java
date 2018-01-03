package com.monitor.web.controller.test;

import com.cloud.api.vo.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author summer
 */
@Api
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "测试接口", notes = "测试")
    @GetMapping("/{id}")
    public ResultCode<Void> test(@PathVariable Integer id) {
        return ResultCode.SUCCESS;
    }
}
