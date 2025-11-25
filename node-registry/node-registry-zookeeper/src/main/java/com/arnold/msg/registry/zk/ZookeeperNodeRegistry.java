package com.arnold.msg.registry.zk;

import com.arnold.msg.registry.DataNode;
import com.arnold.msg.registry.JsonUtils;
import com.arnold.msg.registry.NodeRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ZookeeperNodeRegistry extends NodeRegistry {

    private final CuratorFramework client;
    private final String basePath;
    private final PathChildrenCache pathChildrenCache;

    public ZookeeperNodeRegistry(String zkAddress, String basePath) throws Exception {
        this.basePath = basePath;
        this.client = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        this.client.start();

        this.pathChildrenCache = new PathChildrenCache(client, basePath, true);
        this.pathChildrenCache.getListenable().addListener(
                new ZookeeperPathChangeCapture());
        this.pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

    }

    @Override
    public String registerNode(DataNode node) throws Exception {
        String path = basePath + "/" + node.getKey();
        String nodeData = JsonUtils.toJson(node);
        String createdPath = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, nodeData.getBytes());
        log.info("Registered node: {}", createdPath);
        return createdPath;
    }

    @Override
    public List<DataNode> getActiveNodes() {
        return pathChildrenCache.getCurrentData().stream()
                .map(childData -> {
                    String nodeInfo = new String(childData.getData());
                    return JsonUtils.fromJson(nodeInfo, DataNode.class);
                })
                .collect(Collectors.toList());
    }

    private class ZookeeperPathChangeCapture implements PathChildrenCacheListener {

        @Override
        public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) {
            log.debug("receive zk change event {}", event.getType());
            switch (event.getType()) {
                case CHILD_ADDED:
                case CHILD_REMOVED:
                case CHILD_UPDATED:
                case INITIALIZED:
                    refreshAllDataNodes();
                    return;
                default:
                    // do nothing
            }
        }
    }
}
