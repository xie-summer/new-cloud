package com.cloud.web.controller.user;

import com.cloud.api.vo.ResultCode;
import com.cloud.web.util.WebUtils;
import com.monitor.dto.user.PanoramicUserInfo;
import com.monitor.model.user.PanoramicUser;
import com.panoramic.user.acl.PanoramicUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by @author summer on 2017/11/07.
 */
@RestController
@RequestMapping("/user")
public class PanoramicUserController {
    @Autowired
    @Qualifier("userService")
    private PanoramicUserService panoramicUserService;

    @PostMapping
    public ResultCode<PanoramicUser> add(PanoramicUser panoramicUser) {
        panoramicUserService.save(panoramicUser);
        return ResultCode.getSuccessReturn(panoramicUser);
    }

    @DeleteMapping("/{id}")
    public ResultCode<PanoramicUser> delete(@PathVariable Integer id) {
        panoramicUserService.deleteById(id);
        return ResultCode.getSuccessMap();
    }

    @PutMapping
    public ResultCode<PanoramicUser> update(PanoramicUser panoramicUser) {
        panoramicUserService.update(panoramicUser);
        return ResultCode.getSuccessReturn(panoramicUser);
    }

//    @GetMapping("/{id}")
//    public ResultCode<PanoramicUser> detail(@PathVariable Integer id) {
//        PanoramicUser panoramicUser = panoramicUserService.findById(id);
//        return ResultCode.getSuccessReturn(panoramicUser);
//    }

//    @GetMapping
//    public ResultCode<PageInfo> list(Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        List<PanoramicUser> list = panoramicUserService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultCode.getSuccessReturn(pageInfo);
//    }

    @ApiOperation(value = "登录接口", notes = "根据输入用户密码获取用户基本信息")
    @PostMapping(value = "/login")
    public ResultCode<PanoramicUserInfo> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        PanoramicUserInfo panoramicUserMobile = panoramicUserService.getUserInfo(username, password);
        return ResultCode.getSuccessReturn(panoramicUserMobile);
    }

    @ApiOperation(value = "登录接口", notes = "根据用户名密码登录到系统")
    @PostMapping(value = "/weblogin")
    public ResultCode<PanoramicUserInfo> weblogin(@RequestParam("username") String username, @RequestParam("password") String password,
                                                  HttpServletResponse response, HttpServletRequest request) {
        PanoramicUserInfo panoramicUserWeb = panoramicUserService.loginWeb(username, password);
        if (panoramicUserWeb != null) {
            WebUtils.setLoginMemberKey(request, response);
        }
        return ResultCode.getSuccessReturn(panoramicUserWeb);
    }

    @ApiOperation(value = "登出接口", notes = "根据输入用户密码获取用户基本信息")
    @RequestMapping(value = "/weblogout")
    public ResultCode<PanoramicUserInfo> weblogout(@RequestParam("username") String username,
                                                   HttpServletResponse response, HttpServletRequest request) {
        WebUtils.removeLoginMemberKey(request, response);
        return ResultCode.getSuccess("登出成功");
    }
}
