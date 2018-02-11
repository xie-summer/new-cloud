package com.itopener.sequence.spring.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itopener.sequence.spring.boot.autoconfigure.support.IWorker;
import com.itopener.sequence.spring.boot.autoconfigure.support.Sequence;

/**  
 * @author fuwei.deng
 * @date 2018年1月25日 下午4:21:10
 * @version 1.0.0
 */
@Configuration
public class SequenceAutoConfiguration {
	
	@Bean
	@ConditionalOnBean(IWorker.class)
	public Sequence sequence(IWorker worker) {
		return new Sequence(worker);
	}
}
