package com.itopener.zuul.route.zk.spring.boot.autoconfigure;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * @author fuwei.deng
 * @date 2017年7月1日 上午10:28:39
 * @version 1.0.0
 */
public class CuratorFrameworkClient {

	private final Logger logger = LoggerFactory.getLogger(CuratorFrameworkClient.class);

	private CuratorFramework curatorFramework;

	private TreeCache treeCache;
	
	@Autowired
	private ZuulRouteZookeeperProperties zuulZookeeperRouteProperties;

	public void init() {
		ZuulRouteZookeeperProperties.Zookeeper zkConfig = zuulZookeeperRouteProperties.getZk();
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
		curatorFramework = builder.build();
		curatorFramework.start();
		treeCache = TreeCache.newBuilder(curatorFramework, "/").build();
		try {
			treeCache.start();
		} catch (Exception e) {
			logger.error("zk tree cache exception", e);
		}
		try {
			if (!curatorFramework.blockUntilConnected(zkConfig.getMaxSleepTimeMilliseconds() * zkConfig.getMaxRetries(), TimeUnit.MILLISECONDS)) {
				curatorFramework.close();
				throw new KeeperException.OperationTimeoutException();
			}
		} catch (Exception e) {
			logger.error("zk exception", e);
		}
	}

	public String get(final String key) {
		ChildData resultInCache = treeCache.getCurrentData(key);
		if (null != resultInCache) {
			byte[] data = resultInCache.getData();
			if(data != null){
				return new String(data, Charsets.UTF_8);
			}
		}
		return getDirectly(key);
	}

	private String getDirectly(final String key) {
		try {
			return new String(curatorFramework.getData().forPath(key), Charsets.UTF_8);
		} catch (Exception e) {
			logger.error("zk exception", e);
		}
		return null;
	}

	public List<String> getChildrenKeys(final String key) {
		try {
			List<String> result = curatorFramework.getChildren().forPath(key);
			Collections.sort(result, new Comparator<String>() {
				@Override
				public int compare(final String o1, final String o2) {
					return o2.compareTo(o1);
				}
			});
			return result;
		} catch (Exception e) {
			logger.error("zk exception", e);
			return Collections.emptyList();
		}
	}

	public int getNumChildren(final String key) {
		try {
			Stat stat = curatorFramework.checkExists().forPath(key);
			if (null != stat) {
				return stat.getNumChildren();
			}
		} catch (Exception e) {
			logger.error("zk exception", e);
		}
		return 0;
	}

	public boolean isExisted(final String key) {
		try {
			return null != curatorFramework.checkExists().forPath(key);
		} catch (final Exception ex) {
			return false;
		}
	}

	public void persist(final String key, final String value) {
		try {
			if (!isExisted(key)) {
				curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(key,
						value.getBytes(Charsets.UTF_8));
			} else {
				update(key, value);
			}
		} catch (Exception e) {
			logger.error("persist to zk exception", e);
		}
	}

	public void update(final String key, final String value) {
		try {
			curatorFramework.inTransaction().check().forPath(key).and().setData().forPath(key, value.getBytes(Charsets.UTF_8))
					.and().commit();
		} catch (Exception e) {
			logger.error("persist to zk exception", e);
		}
	}

	public void persistEphemeral(final String key, final String value) {
		try {
			if (isExisted(key)) {
				curatorFramework.delete().deletingChildrenIfNeeded().forPath(key);
			}
			curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key,
					value.getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			logger.error("persist to zk exception", e);
		}
	}

	public String persistSequential(final String key, final String value) {
		try {
			return curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(key,
					value.getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			logger.error("persist to zk exception", e);
		}
		return null;
	}

	public void persistEphemeralSequential(final String key) {
		try {
			curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(key);
		} catch (Exception e) {
			logger.error("persist to zk exception", e);
		}
	}

	public void remove(final String key) {
		try {
			if(isExisted(key)){
				curatorFramework.delete().deletingChildrenIfNeeded().forPath(key);
			}
		} catch (Exception e) {
			logger.error("remove from zk exception", e);
		}
	}

	public long getRegistryCenterTime(final String key) {
		long result = 0L;
		try {
			persist(key, "");
			result = curatorFramework.checkExists().forPath(key).getMtime();
		} catch (Exception e) {
			logger.error("zk exception", e);
		}
		Preconditions.checkState(0L != result, "Cannot get registry center time.");
		return result;
	}

	public CuratorFramework getCuratorFramework() {
		return curatorFramework;
	}

	public TreeCache getTreeCache() {
		return treeCache;
	}
	
}