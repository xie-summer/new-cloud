package com.cloud.zuul.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
/** @author xieshengrong */
@Component
@Primary
@Configuration
public class DocumentationConfig implements SwaggerResourcesProvider {

  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();
    //zuul 中聚合api doc
    resources.add(swaggerResource("config-servers", "/config-servers/v2/api-docs", "2.0"));
    resources.add(swaggerResource("manage", "/manage/v2/api-docs", "2.0"));
    return resources;
  }

  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
