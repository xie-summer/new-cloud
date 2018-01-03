package com.cloud.support.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author summer
 */
@Deprecated
public class DataSourceAspect {

    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
        //clear datasource before support service with many same dataSource mapper
//        DynamicDataSourceHolders.putDataSource(null);
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource date = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolders.putDataSource(date.value());
                //  System.out.println(date.value()+"----------------");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
