package com.cloud.zuul.componet.filter.pre;

import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SystemPublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * @author summer  基于内存压力的限流——当可用内存低于某个阈值就开启限流，否则不开启限流。
 */
@Component
public class MemRateLimitZuulFilter extends ZuulFilter {
    @Autowired
    private SystemPublicMetrics systemPublicMetrics;
    @Override
    public boolean shouldFilter() {
        // 这里可以考虑弄个限流开启的开关，开启限流返回true，关闭限流返回false，你懂的。
        Collection<Metric<?>> metrics = systemPublicMetrics.metrics();
        Optional<Metric<?>> freeMemoryMetric = metrics.stream()
                .filter(t -> "mem.free".equals(t.getName()))
                .findFirst();
        // 如果不存在这个指标，稳妥起见，返回true，开启限流
        if (!freeMemoryMetric.isPresent()) {
            return true;
        }
        long freeMemory = freeMemoryMetric.get()
                .getValue()
                .longValue();
        // 如果可用内存小于1000000KB，开启流控
        return freeMemory < 1000000L;
    }

    @Override
    public Object run() {
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
