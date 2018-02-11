package com.itopener.zuul.route.zk.spring.boot.autoconfigure;

import javax.annotation.Resource;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.itopener.zuul.route.spring.boot.common.refresh.RefreshRouteService;

public class ZookeeperTreeCacheListener {
	
	private final Logger logger = LoggerFactory.getLogger(ZookeeperTreeCacheListener.class);

	@Autowired
	private CuratorFrameworkClient curatorFrameworkClient;
	
	@Resource
	private RefreshRouteService refreshRouteService;
	
	public void init(){
		TreeCache treeCache = curatorFrameworkClient.getTreeCache();
		treeCache.getListenable().addListener(new TreeCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				logger.info("tree cache listener");
				refreshRouteService.refreshRoute();
			}
		});
	}
}
