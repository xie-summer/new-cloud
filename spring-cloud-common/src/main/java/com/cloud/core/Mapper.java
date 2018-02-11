package com.cloud.core;

import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 *
 * @author summer
 */
public interface Mapper<T>
        extends
        MySqlMapper<T>,
        BaseMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        InsertListMapper<T>,
        ExampleMapper<T>,
        RowBoundsMapper<T>,
        Marker {

}
