package com.cloud.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author summer
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired(required = false)
    protected Mapper<T> mapper;

    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> clazz;
    /**
     * 当前泛型真实类型实体
     */
    private T model;

    public AbstractService() {
        try {
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (!(genericSuperclass instanceof ParameterizedType)) {
                genericSuperclass = this.getClass().getSuperclass().getGenericSuperclass();
            }
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Class clazz = (Class) parameterizedType.getActualTypeArguments()[0];
            model = (T) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(T model) {
        mapper.insertSelective(model);
    }

    @Override
    public void save(List<T> models) {
        mapper.insertList(models);
    }

    @Override
    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String property, Object value) throws TooManyResultsException {
        try {
            T model = clazz.newInstance();
            Field field = clazz.getDeclaredField(property);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }
}
