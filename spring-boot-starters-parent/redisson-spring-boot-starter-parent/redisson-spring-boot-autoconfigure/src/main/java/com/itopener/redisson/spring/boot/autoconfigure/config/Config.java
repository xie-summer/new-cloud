package com.itopener.redisson.spring.boot.autoconfigure.config;

import org.redisson.codec.DefaultCodecProvider;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.liveobject.provider.DefaultResolverProvider;

/**
 * @author fuwei.deng
 * @date 2018年1月5日 下午1:59:50
 * @version 1.0.0
 */
public class Config {

	private SentinelServersConfig sentinelServersConfig;

	private MasterSlaveServersConfig masterSlaveServersConfig;

	private SingleServerConfig singleServerConfig;

	private ClusterServersConfig clusterServersConfig;

	private ReplicatedServersConfig replicatedServersConfig;

	/**
	 * Threads amount shared between all redis node clients
	 */
	private int threads = 0; // 0 = current_processors_amount * 2

	private int nettyThreads = 0; // 0 = current_processors_amount * 2

	/**
	 * Redis key/value codec. JsonJacksonCodec used by default
	 */
	private ClassProperty codec = new ClassProperty(JsonJacksonCodec.class.getName());

	/**
	 * For codec registry and look up. DefaultCodecProvider used by default
	 */
	private ClassProperty codecProvider = new ClassProperty(DefaultCodecProvider.class.getName());

	/**
	 * For resolver registry and look up. DefaultResolverProvider used by default
	 */
	private ClassProperty resolverProvider = new ClassProperty(DefaultResolverProvider.class.getName());

//	private Class<? extends ExecutorService> executor;

	/**
	 * Config option for enabling Redisson Reference feature. Default value is TRUE
	 */
	private boolean redissonReferenceEnabled = true;

	private boolean useLinuxNativeEpoll;

//	private Class<? extends EventLoopGroup> eventLoopGroup;

	private long lockWatchdogTimeout = 30 * 1000;

	private boolean keepPubSubOrder = true;

	public SentinelServersConfig getSentinelServersConfig() {
		return sentinelServersConfig;
	}

	public void setSentinelServersConfig(SentinelServersConfig sentinelServersConfig) {
		this.sentinelServersConfig = sentinelServersConfig;
	}

	public MasterSlaveServersConfig getMasterSlaveServersConfig() {
		return masterSlaveServersConfig;
	}

	public void setMasterSlaveServersConfig(MasterSlaveServersConfig masterSlaveServersConfig) {
		this.masterSlaveServersConfig = masterSlaveServersConfig;
	}

	public SingleServerConfig getSingleServerConfig() {
		return singleServerConfig;
	}

	public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
		this.singleServerConfig = singleServerConfig;
	}

	public ClusterServersConfig getClusterServersConfig() {
		return clusterServersConfig;
	}

	public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
		this.clusterServersConfig = clusterServersConfig;
	}

	public ReplicatedServersConfig getReplicatedServersConfig() {
		return replicatedServersConfig;
	}

	public void setReplicatedServersConfig(ReplicatedServersConfig replicatedServersConfig) {
		this.replicatedServersConfig = replicatedServersConfig;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getNettyThreads() {
		return nettyThreads;
	}

	public void setNettyThreads(int nettyThreads) {
		this.nettyThreads = nettyThreads;
	}

//	public Class<? extends ExecutorService> getExecutor() {
//		return executor;
//	}
//
//	public void setExecutor(Class<? extends ExecutorService> executor) {
//		this.executor = executor;
//	}

	public ClassProperty getCodec() {
		return codec;
	}

	public void setCodec(ClassProperty codec) {
		this.codec = codec;
	}

	public ClassProperty getCodecProvider() {
		return codecProvider;
	}

	public void setCodecProvider(ClassProperty codecProvider) {
		this.codecProvider = codecProvider;
	}

	public ClassProperty getResolverProvider() {
		return resolverProvider;
	}

	public void setResolverProvider(ClassProperty resolverProvider) {
		this.resolverProvider = resolverProvider;
	}

	public boolean isRedissonReferenceEnabled() {
		return redissonReferenceEnabled;
	}

	public void setRedissonReferenceEnabled(boolean redissonReferenceEnabled) {
		this.redissonReferenceEnabled = redissonReferenceEnabled;
	}

	public boolean isUseLinuxNativeEpoll() {
		return useLinuxNativeEpoll;
	}

	public void setUseLinuxNativeEpoll(boolean useLinuxNativeEpoll) {
		this.useLinuxNativeEpoll = useLinuxNativeEpoll;
	}

//	public Class<? extends EventLoopGroup> getEventLoopGroup() {
//		return eventLoopGroup;
//	}
//	
//	public void setEventLoopGroup(Class<? extends EventLoopGroup> eventLoopGroup) {
//		this.eventLoopGroup = eventLoopGroup;
//	}

	public long getLockWatchdogTimeout() {
		return lockWatchdogTimeout;
	}

	public void setLockWatchdogTimeout(long lockWatchdogTimeout) {
		this.lockWatchdogTimeout = lockWatchdogTimeout;
	}

	public boolean isKeepPubSubOrder() {
		return keepPubSubOrder;
	}

	public void setKeepPubSubOrder(boolean keepPubSubOrder) {
		this.keepPubSubOrder = keepPubSubOrder;
	}

}
