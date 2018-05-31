package com.cloud.zuul.common.exception;

import com.netflix.zuul.exception.ZuulException;

/**
 * @author summer  分布式限流异常
 */
public class RateLimiterException  extends ZuulException {

    private static final long serialVersionUID = 1L;


    /**
     * Source Throwable, message, status code and info about the cause
     *
     * @param throwable
     * @param sMessage
     * @param nStatusCode
     * @param errorCause
     */
    public RateLimiterException(Throwable throwable, String sMessage, int nStatusCode, String errorCause) {
        super(throwable, sMessage, nStatusCode, errorCause);
    }

    /**
     * error message, status code and info about the cause
     *
     * @param sMessage
     * @param nStatusCode
     * @param errorCause
     */
    public RateLimiterException(String sMessage, int nStatusCode, String errorCause) {
        super(sMessage, nStatusCode, errorCause);
    }

    /**
     * Source Throwable,  status code and info about the cause
     *
     * @param throwable
     * @param nStatusCode
     * @param errorCause
     */
    public RateLimiterException(Throwable throwable, int nStatusCode, String errorCause) {
        super(throwable, nStatusCode, errorCause);
    }
}