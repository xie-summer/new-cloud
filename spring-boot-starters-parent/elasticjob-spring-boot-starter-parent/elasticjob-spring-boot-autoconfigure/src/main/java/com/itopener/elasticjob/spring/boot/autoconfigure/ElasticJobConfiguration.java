package com.itopener.elasticjob.spring.boot.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.itopener.elasticjob.spring.boot.autoconfigure.ElasticJobProperties.JobProps;

/**
 * @author fuwei.deng
 * @date 2017年6月14日 下午3:11:22
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(ElasticJobProperties.class)
@ImportAutoConfiguration(ElasticJobEventConfiguration.class)
public class ElasticJobConfiguration {

	@Autowired
	private ElasticJobProperties elasticJobProperties;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired(required = false) 
	private JobEventConfiguration jobEventRdbConfiguration;

	@Bean(initMethod = "init")
	public ZookeeperRegistryCenter regCenter() {
		ZookeeperConfiguration zk = new ZookeeperConfiguration(elasticJobProperties.getZk().getServerLists(), elasticJobProperties.getZk().getNamespace());
		zk.setBaseSleepTimeMilliseconds(elasticJobProperties.getZk().getBaseSleepTimeMilliseconds());
		zk.setConnectionTimeoutMilliseconds(elasticJobProperties.getZk().getConnectionTimeoutMilliseconds());
		zk.setDigest(elasticJobProperties.getZk().getDigest());
		zk.setMaxRetries(elasticJobProperties.getZk().getMaxRetries());
		zk.setMaxSleepTimeMilliseconds(elasticJobProperties.getZk().getMaxSleepTimeMilliseconds());
		zk.setSessionTimeoutMilliseconds(elasticJobProperties.getZk().getSessionTimeoutMilliseconds());
		return new ZookeeperRegistryCenter(zk);
	}

	@Bean
	@ConditionalOnBean(SimpleJob.class)
    public JobSchedulerFactory simpleJobScheduler(ZookeeperRegistryCenter regCenter) {
		Map<String, JobProps> jobs = elasticJobProperties.getSimpleJobs();
		List<JobScheduler> JobSchedulerList = new ArrayList<JobScheduler>();
		for(String jobName : jobs.keySet()) {
			SimpleJob simpleJob = applicationContext.getBean(jobName, SimpleJob.class);
			if(simpleJob == null){
				continue;
			}
			JobProps jobProps = jobs.get(jobName);
			JobScheduler jobScheduler = null;
			if(jobEventRdbConfiguration == null){
				jobScheduler = new SpringJobScheduler(simpleJob, regCenter, getLiteSimpleJobConfiguration(simpleJob.getClass(), jobProps.getCron(), jobProps.getShardingTotalCount(), jobProps.getShardingItemParameters()));
			} else{
				jobScheduler = new SpringJobScheduler(simpleJob, regCenter, getLiteSimpleJobConfiguration(simpleJob.getClass(), jobProps.getCron(), jobProps.getShardingTotalCount(), jobProps.getShardingItemParameters()), jobEventRdbConfiguration);
			}
			jobScheduler.init();
			JobSchedulerList.add(jobScheduler);
		}
		return new SimpleJobSchedulerFactory(JobSchedulerList);
    }
	
	@Bean
	@ConditionalOnBean(DataflowJob.class)
	public JobSchedulerFactory dataflowJobScheduler(ZookeeperRegistryCenter regCenter) {
		Map<String, JobProps> jobs = elasticJobProperties.getDataflowJobs();
		List<JobScheduler> JobSchedulerList = new ArrayList<JobScheduler>();
		for(String jobName : jobs.keySet()) {
			DataflowJob<?> dataflowJob = applicationContext.getBean(jobName, DataflowJob.class);
			if(dataflowJob == null){
				continue;
			}
			JobProps jobProps = jobs.get(jobName);
			JobScheduler jobScheduler = null;
			if(jobEventRdbConfiguration == null){
				jobScheduler = new SpringJobScheduler(dataflowJob, regCenter, getLiteDataflowJobConfiguration(dataflowJob.getClass(), jobProps.getCron(), jobProps.getShardingTotalCount(), jobProps.getShardingItemParameters()));
			} else{
				jobScheduler = new SpringJobScheduler(dataflowJob, regCenter, getLiteDataflowJobConfiguration(dataflowJob.getClass(), jobProps.getCron(), jobProps.getShardingTotalCount(), jobProps.getShardingItemParameters()), jobEventRdbConfiguration);
			}
			jobScheduler.init();
			JobSchedulerList.add(jobScheduler);
		}
		return new DataflowJobSchedulerFactory(JobSchedulerList);
	}
	
	class JobSchedulerFactory {
		
		private List<JobScheduler> jobSchedulerList;
		
		public JobSchedulerFactory() {
		}

		public JobSchedulerFactory(List<JobScheduler> jobSchedulerList) {
			this.jobSchedulerList = jobSchedulerList;
		}

		public List<JobScheduler> getJobSchedulerList() {
			return jobSchedulerList;
		}

		public void setJobSchedulerList(List<JobScheduler> jobSchedulerList) {
			this.jobSchedulerList = jobSchedulerList;
		}
		
	}
	
	class SimpleJobSchedulerFactory extends JobSchedulerFactory {
		public SimpleJobSchedulerFactory(List<JobScheduler> jobSchedulerList) {
			super(jobSchedulerList);
		}
	}
	
	class DataflowJobSchedulerFactory extends JobSchedulerFactory {
		public DataflowJobSchedulerFactory(List<JobScheduler> jobSchedulerList) {
			super(jobSchedulerList);
		}
	}
	
	private LiteJobConfiguration getLiteSimpleJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(false).build();
    }
	
	@SuppressWarnings("rawtypes")
	private LiteJobConfiguration getLiteDataflowJobConfiguration(final Class<? extends DataflowJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new DataflowJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName(), true)).overwrite(false).build();
    }
}
