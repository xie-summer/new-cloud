package com.cloud.web.support.oss;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 必须配置开启断路器,默认是false
 * @author summer
 */
@FeignClient(name = "oss-provider", fallback = OssFeignHystrixClient.HystrixClientFallback.class)
public interface OssFeignHystrixClient {

    @GetMapping("/aliyun/oss/policy")
    public Object policy(@RequestParam(required = false) String callback);

    /**
     * 这边采取了和Spring Cloud官方文档相同的做法，将fallback类作为内部类放入Feign的接口中，当然也可以单独写一个fallback类。
     *
     * @author eacdy
     */
    @Component
    class HystrixClientFallback implements OssFeignHystrixClient {
        private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallback.class);

        /**
         * hystrix fallback方法
         *
         * @param callback
         * @return 默认的返回null
         */
        @Override
        public Object policy(String callback) {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：callback = {}", callback);
            if (StringUtils.isNotBlank(callback)) {
                LOGGER.info("异常了默认回调 :" + callback);
                return callback;
            }
            return null;
        }


    }
}
