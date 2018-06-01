package com.cloud.elk;

import com.cloud.util.LoggerUtils;
import com.cloud.util.TLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/** @author summer 2018/6/1 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinServer
public class ElkApplication {
  private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(ElkApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ElkApplication.class, args);
    DB_LOGGER.warn("ZuulApplication started successfully");
  }
}
