package com.risk.warning.configure;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.cloud.core.Mapper;
import com.github.pagehelper.PageHelper;
import com.risk.warning.constant.MybatisConstant;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * @author summer Mybatis & Mapper & PageHelper 配置
 */
@Configuration
public class MybatisConfigurer {
	@Resource
	private DataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setTypeAliasesPackage(MybatisConstant.MODEL_PACKAGE);
		// 分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);
		bean.setPlugins(new Interceptor[] { pageHelper });
		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(resolver.getResources("classpath:mapper/**.xml"));
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
			// 配置通用mappers(tk通用mapper)
			Properties properties = new Properties();
			properties.setProperty("mappers", Mapper.class.getName());
			properties.setProperty("notEmpty", "false");
			properties.setProperty("IDENTITY", "MYSQL");
			mapperScannerConfigurer.setProperties(properties);

			return mapperScannerConfigurer;
		}

	}
}
