package com.cloud.butler.configure;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieshengrong
 */
@Data
@ConfigurationProperties("swagger.butler")
public class SwaggerButlerProperties {

    /**
     * 配置静态文档地址
     */
    private List<SwaggerResourceProperties> resources = new ArrayList<>();

    /**
     * 不显示没有实例的服务
     */
    private Boolean notShowNoInstanceService = true;

    /**
     * Swagger返回JSON文档的接口路径
     */
    private String apiDocsPath = "/v2/api-docs";

}
