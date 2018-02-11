package com.cloud.configure;

import com.cloud.constant.MybatisConstant;
import com.cloud.core.Mapper;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Mybatis & Mapper & PageHelper 配置
 * @author summer
 */
@Configuration
public class MybatisConfigurer {
    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(MybatisConstant.MODEL_PACKAGE);
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        /**abel533通用mapper*/
//        MapperInterceptor mapperInterceptor = new MapperInterceptor();
//        Properties props = new Properties();
//        /**--主键自增回写方法,默认值MYSQL,详细说明请看文档 -->*/
//        props.setProperty("IDENTITY","MYSQL");
//        /**<!--通用Mapper接口，多个通用接口用逗号隔开 -->*/
//        props.setProperty("mappers", "com.github.abel533.mapper.Mapper");
//        mapperInterceptor.setProperties(props);
        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});
//        bean.setPlugins(new Interceptor[]{mapperInterceptor});


        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Configuration
    @AutoConfigureAfter(MybatisConfigurer.class)
    public static class MyBatisMapperScannerConfigurer {

        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
            mapperScannerConfigurer.setBasePackage(MybatisConstant.MAPPER_PACKAGE);
            //配置通用mappers(tk通用mapper)
            Properties properties = new Properties();
            properties.setProperty("mappers", Mapper.class.getName());
//            properties.setProperty("mappers", ProjectConstant.MAPPER_BASE_PACKAGE);
            properties.setProperty("notEmpty", "false");
            properties.setProperty("IDENTITY", "MYSQL");
            mapperScannerConfigurer.setProperties(properties);

            return mapperScannerConfigurer;
        }

    }
}

