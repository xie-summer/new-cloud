package com.cloud.support.controller.aop;

import com.cloud.core.ServiceException;
import com.cloud.support.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Clock;

/**
 * controller控制层AOP 对异常处理,日志记录
 * @author summer
 */
@Component
public class ControllerAop {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    @Around("execution(* com.*.*.*ErrorCode *(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = Clock.systemDefaultZone().millis();
        ErrorCode<?> result;
        try {
            result = (ErrorCode<?>) pjp.proceed();
            logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }finally {
            after();
        }
        return result;
    }
private void after(){

}
    private ErrorCode<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ErrorCode<?> result = new ErrorCode();

        // 已知异常
        if (e instanceof ServiceException) {
            result.setMsg(e.getLocalizedMessage());
            result.setErrcode(ErrorCode.CODE_KNOWN_ERROR);
        } else {
            logger.error(pjp.getSignature() + " error ", e);

            result.setMsg(e.toString());
            result.setErrcode(ErrorCode.CODE_UNKNOWN_ERROR);

            // 未知异常是应该重点关注的，这里可以做其他操作，如通知邮件，单独写到某个文件等等。
        }

        return result;
    }
}
