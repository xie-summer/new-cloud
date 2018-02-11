package com.itopener.lock.zk.spring.boot.autoconfigure;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.itopener.lock.zk.spring.boot.autoconfigure.lock.DistributedLock;
import com.itopener.lock.zk.spring.boot.autoconfigure.lock.ZookeeperDistributedLock;

/**  
 * @author fuwei.deng
 * @Date 2017年6月9日 下午3:10:58
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(ZookeeperDistributedLockProperties.class)
public class ZookeeperDistributedLockAutoConfiguration {
	
	private final Logger logger = LoggerFactory.getLogger(ZookeeperDistributedLockAutoConfiguration.class);
	
	@Autowired
	private ZookeeperDistributedLockProperties zookeeperDistributedLockProperties;

	@Bean
	public CuratorFramework curatorFramework(){
		ZookeeperDistributedLockProperties.Zookeeper zkConfig = zookeeperDistributedLockProperties.getZk();
		logger.debug("zookeeper registry center init, server lists is: {}.", zkConfig.getServerLists());
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
				.connectString(zkConfig.getServerLists())
				.retryPolicy(new ExponentialBackoffRetry(zkConfig.getBaseSleepTimeMilliseconds(),
						zkConfig.getMaxRetries(), zkConfig.getMaxSleepTimeMilliseconds()))
				.namespace(zkConfig.getNamespace());
		if (0 != zkConfig.getSessionTimeoutMilliseconds()) {
			builder.sessionTimeoutMs(zkConfig.getSessionTimeoutMilliseconds());
		}
		if (0 != zkConfig.getConnectionTimeoutMilliseconds()) {
			builder.connectionTimeoutMs(zkConfig.getConnectionTimeoutMilliseconds());
		}
		if (!Strings.isNullOrEmpty(zkConfig.getDigest())) {
			builder.authorization("digest", zkConfig.getDigest().getBytes(Charsets.UTF_8))
					.aclProvider(new ACLProvider() {

						@Override
						public List<ACL> getDefaultAcl() {
							return ZooDefs.Ids.CREATOR_ALL_ACL;
						}

						@Override
						public List<ACL> getAclForPath(final String path) {
							return ZooDefs.Ids.CREATOR_ALL_ACL;
						}
					});
		}
		CuratorFramework curatorFramework = builder.build();
		curatorFramework.start();
		try {
			if (!curatorFramework.blockUntilConnected(zkConfig.getMaxSleepTimeMilliseconds() * zkConfig.getMaxRetries(), TimeUnit.MILLISECONDS)) {
				curatorFramework.close();
				throw new KeeperException.OperationTimeoutException();
			}
		} catch (Exception e) {
			logger.error("zk exception", e);
		}
        return curatorFramework;
	}
	
	@Bean
	@ConditionalOnBean(CuratorFramework.class)
	public DistributedLock zookeeperDistributedLock(CuratorFramework curatorFramework){
		return new ZookeeperDistributedLock(curatorFramework);
	}
}
