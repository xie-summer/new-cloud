//package com.cloud.zuul.feign;
//
//import com.cloud.zuul.common.vo.UserVo;
//import com.cloud.zuul.feign.fallback.UserServiceFallbackImpl;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
///**
// * @author summer
// * @date 2017/10/31
// */
//@FeignClient(name = "cloud-user-server", fallback = UserServiceFallbackImpl.class)
//@Service
//public interface UserServiceClient extends UserService {
//  /**
//   * 通过用户名查询用户、角色信息
//   *
//   * @param username 用户名
//   * @return UserVo
//   */
//  @GetMapping("/user/findUserByUsername/{username}")
//  UserVo findUserByUsername(@PathVariable("username") String username);
//
//  /**
//   * @param id
//   * @return
//   */
//  @HystrixCollapser(
//    batchMethod = "findAll",
//    collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "100")}
//  )
//  public UserVo find(Long id);
//
//  /**
//   * @param ids
//   * @return
//   */
//  @HystrixCommand
//  @GetMapping("/user/{ids}")
//  List<UserVo> findAll(List<Long> ids);
//}
