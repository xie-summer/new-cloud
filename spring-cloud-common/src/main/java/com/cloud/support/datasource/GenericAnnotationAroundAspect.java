package com.cloud.support.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author summer
 */
public abstract class GenericAnnotationAroundAspect {

    private static final Logger log = LoggerFactory.getLogger(GenericAnnotationAroundAspect.class);

    //TODO 环绕通知拦截方法
    @Around("execution(* com.*.*.service*.*Impl(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            before(point);
            return point.proceed(point.getArgs());
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            after();
        }
    }


    public void after() {
        DynamicDataSourceHolders.putDataSource(null);
    }

    public void before(ProceedingJoinPoint pjp) {
        if (pjp instanceof MethodInvocationProceedingJoinPoint) {
            MethodInvocationProceedingJoinPoint mpjp = (MethodInvocationProceedingJoinPoint) pjp;
            MethodSignature methodSignature = (MethodSignature) mpjp.getSignature();
            //获取当前的切面方法
            Method method = methodSignature.getMethod();
            DynamicDataSourceHolders.putDataSource(null);
            Class target = mpjp.getTarget().getClass();
            boolean collectResult = collectResult(target, method);
            if (collectResult) {
                log.debug("{}方法{}数据源代理到{}", Thread.currentThread(), method.getName(), DynamicDataSourceHolders.getDataSource());
            } else {
                log.debug("{}方法{}无数据源代理注解", Thread.currentThread(), method.getName());
            }
        }

    }

    private boolean collectResult(Class target, Method method) {
        //先接口中遍历注解
        Class superclass = target.getSuperclass();
        if (superclass != null) {
            if (collectResult(superclass, method)) {
                return true;
            }
        }
        Class[] interfaces = target.getInterfaces();
        if (interfaces != null) {
            for (Class anInterface : interfaces) {
                if (collectResult(anInterface, method)) {
                    return true;
                }
            }
        }
        //最后遍历父类注解
        Method expectMethod1 = findoutImplementedMethodByInterFaces(target, method);
        if (expectMethod1 != null) {
            return process(expectMethod1);
        }
        Method expectMethod2 = findoutOverrideOrImplementedMethodInSuperClz(target, method);
        if (expectMethod2 != null) {
            return process(expectMethod2);
        }
        return false;

    }

    private Method findoutOverrideOrImplementedMethodInSuperClz(Class targetClass, Method method) {
        Method superMethod = null;
        //无可追溯的父类
        if (targetClass == null || targetClass == Object.class) {
            return null;
        }

        try {
            //todo　这里不使用参数进行匹配，有可能父类是个抽象类，其中参数是泛型，无法获取方法，避免这种情况，方法不应该重载参数相同的方法,或者不要使用泛型
            superMethod = targetClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return superMethod;
        } catch (NoSuchMethodException e) {
            //如果无法准确获取遍历重名方法是否有直接获取,使用了泛型的方法往往不需要重载，插边球
            Method[] declaredMethods = targetClass.getDeclaredMethods();
            if (declaredMethods != null) {
                for (Method declaredMethod : declaredMethods) {
                    if (method.getName().equals(declaredMethod.getName())) {
                        return declaredMethod;
                    }
                }
            }
        }
        return null;

    }

    private Method findoutImplementedMethodByInterFaces(Class targetInterface, Method method) {
        Method superMethod = null;
        //无可追溯的父类中和接口
        if (targetInterface == null || targetInterface == Object.class) {
            return null;
        }

        try {
            //todo　这里不使用参数进行匹配，有可能接口中参数是泛型，无法获取方法，避免这种情况，方法不应该重载参数相同的方法,或者不要使用泛型
            superMethod = targetInterface.getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (superMethod != null) {
                return superMethod;
            }
        } catch (NoSuchMethodException e) {
            //如果无法准确获取遍历重名方法是否有直接获取,使用了泛型的方法往往不需要重载，插边球
            Method[] declaredMethods = targetInterface.getDeclaredMethods();
            if (declaredMethods != null) {
                for (Method declaredMethod : declaredMethods) {
                    if (method.getName().equals(declaredMethod.getName())) {
                        return declaredMethod;
                    }
                }
            }
        }


        return null;
    }

    public abstract boolean process(Method method);

}

