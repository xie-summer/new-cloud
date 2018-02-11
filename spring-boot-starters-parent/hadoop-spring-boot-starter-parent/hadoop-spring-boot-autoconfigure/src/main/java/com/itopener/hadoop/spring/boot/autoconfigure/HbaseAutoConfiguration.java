package com.itopener.hadoop.spring.boot.autoconfigure;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseConfigurationFactoryBean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {

    @Autowired
    private HbaseProperties hbaseProperties;

    @Bean
    public HbaseConfigurationFactoryBean hbaseConfigurationFactoryBean(){
    	HbaseConfigurationFactoryBean hbaseConfigurationFactoryBean = new HbaseConfigurationFactoryBean();
    	hbaseConfigurationFactoryBean.setZkQuorum(hbaseProperties.getQuorum());
    	hbaseConfigurationFactoryBean.setZkPort(hbaseProperties.getPort());
    	hbaseConfigurationFactoryBean.afterPropertiesSet();
    	return hbaseConfigurationFactoryBean;
    }
    
    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate(HbaseConfigurationFactoryBean hbaseConfigurationFactoryBean) {
        Configuration configuration = hbaseConfigurationFactoryBean.getObject();
        return new HbaseTemplate(configuration);
    }
}
