package com.cloud.admin.web.controller;

import com.cloud.api.vo.ResultCode;
import com.google.common.collect.Lists;
import com.netflix.eureka.resources.InstanceResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.List;

/** @author summer 2018/6/22 */
@RestController
@RequestMapping("/service")
@Api
public class ServiceManageController {
  private DiscoveryClient discoveryClient;
  private EurekaDiscoveryClient eurekaDiscoveryClient;

  @Autowired
  public ServiceManageController(DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
  }

  @GetMapping("/list")
  @ApiOperation(value = "获取注册instance列表")
  public ResultCode<List<ServiceInstance>> getServiceList() {
    List<String> services = discoveryClient.getServices();
    List<ServiceInstance> instances = Lists.newArrayList();
    services.forEach(
        e -> {
          instances.addAll(discoveryClient.getInstances(e));
        });
    return ResultCode.getSuccessReturn(instances);
  }

  @PostMapping("/update/instance/status")
  @ApiOperation(value = "更新instance状态")
  public ResultCode<Response> updateInstance(
      InstanceResource serviceInstance, String status, String lastDirtyTimestamp) {
    Response response = serviceInstance.statusUpdate(status, "false", lastDirtyTimestamp);
    return ResultCode.getSuccessReturn(response);
  }
}
