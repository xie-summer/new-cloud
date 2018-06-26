package com.cloud.eureka.web.controller;

import com.cloud.api.vo.ResultCode;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** @author summer 2018/6/26 */
@RestController
@RequestMapping("/services")
public class ApplicationController {

  private final DiscoveryClient discoveryClient;
  private final EurekaInstanceConfig config;
  private final EurekaClient eurekaClient;

  @Autowired
  public ApplicationController(
      DiscoveryClient discoveryClient, EurekaInstanceConfig config, EurekaClient eurekaClient ) {
    this.discoveryClient = discoveryClient;
    this.config = config;
    this.eurekaClient = eurekaClient;
  }

  @GetMapping("/list")
  public ResultCode<List<ServiceInstance>> getServiceList() {
      Applications applications = eurekaClient.getApplications();
      String instanceId = config.getInstanceId();
      List<String> services = discoveryClient.getServices();
    List<ServiceInstance> instances = discoveryClient.getInstances(instanceId);
    return ResultCode.getSuccessReturn(instances);
  }
}
