package com.cloud.web.controller.user;

import com.cloud.api.vo.ResultCode;
import com.cloud.user.api.member.MemberService;
import com.cloud.web.util.WebUtils;
import com.monitor.domain.dto.user.PanoramicUserInfo;
import com.monitor.model.user.Member;
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
    private MemberService memberService;

    @PostMapping
    public ResultCode<Member> add(Member member) {
        memberService.save(member);
        return ResultCode.getSuccessReturn(member);
    }

    @DeleteMapping("/{id}")
    public ResultCode<Member> delete(@PathVariable Integer id) {
        memberService.deleteById(id);
        return ResultCode.getSuccessMap();
    }

    @PutMapping
    public ResultCode<Member> update(Member member) {
        memberService.update(member);
        return ResultCode.getSuccessReturn(member);
    }

//    @GetMapping("/{id}")
//    public ResultCode<Member> detail(@PathVariable Integer id) {
//        Member panoramicUser = memberService.findById(id);
//        return ResultCode.getSuccessReturn(panoramicUser);
//    }

//    @GetMapping
//    public ResultCode<PageInfo> list(Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        List<Member> list = memberService.findAll();
//        PageInfo pageInfo = new PageInfo(list);
//        return ResultCode.getSuccessReturn(pageInfo);
//    }

    @ApiOperation(value = "登录接口", notes = "根据输入用户密码获取用户基本信息")
    @PostMapping(value = "/login")
    public ResultCode<PanoramicUserInfo> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        PanoramicUserInfo panoramicUserMobile = memberService.getUserInfo(username, password);
        return ResultCode.getSuccessReturn(panoramicUserMobile);
    }

    @ApiOperation(value = "登录接口", notes = "根据用户名密码登录到系统")
    @PostMapping(value = "/weblogin")
    public ResultCode<PanoramicUserInfo> weblogin(@RequestParam("username") String username, @RequestParam("password") String password,
                                                  HttpServletResponse response, HttpServletRequest request) {
        PanoramicUserInfo panoramicUserWeb = memberService.loginWeb(username, password);
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
