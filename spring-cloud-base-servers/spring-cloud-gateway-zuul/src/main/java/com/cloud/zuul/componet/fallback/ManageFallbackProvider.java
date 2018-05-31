package com.cloud.zuul.componet.fallback;

import com.cloud.zuul.constant.ServiceNameConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author summer
 * @date 2018/1/25
 * Auth 模块异常回调
 */
@Component
public class ManageFallbackProvider implements FallbackProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        log.error("调用:{} 异常：{}", getRoute(), cause.getMessage());
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            @Override
            public int getRawStatusCode() {
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            @Override
            public String getStatusText() {
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() {
                if (cause != null && cause.getMessage() != null) {
                    return new ByteArrayInputStream(cause.getMessage().getBytes());
                } else {
                    return new ByteArrayInputStream("manage模块不可用".getBytes());
                }
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    @Override
    public String getRoute() {
        return ServiceNameConstant.MONITOR_MANAGE_SERVICE;
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return fallbackResponse(null);
    }
}