package com.cloud.support.datasource;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author summer
 */
@Component("dataSourceAspect")
public class DefaultDataSourceAroundAspect extends GenericAnnotationAroundAspect {

    @Override
    public boolean process(Method method){
        if(method!=null){
            DataSource dataSourceAnnotation = method.getAnnotation(DataSource.class);
            //父类中找到了注解
            if(dataSourceAnnotation!=null){
                String value = dataSourceAnnotation.value();
                DynamicDataSourceHolders.putDataSource(value);
                return true;
            }
        }
        return false;
    }
}
