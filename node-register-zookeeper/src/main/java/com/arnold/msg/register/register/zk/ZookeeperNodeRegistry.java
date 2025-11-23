package com.arnold.msg.register.register.zk;

import com.arnold.msg.register.DataNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

@Slf4j
public class ZookeeperNodeRegistry {

    private final CuratorFramework client;
    private final String basePath;

    public ZookeeperNodeRegistry(String zkAddress, String basePath) {
        this.basePath = basePath;
        this.client = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        this.client.start();
    }

    public String registerNode(DataNode node) throws Exception {
        String path = basePath + "/" + node.toString();
        String createdPath = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path);
        log.info("Registered node: {}", createdPath);
        return createdPath;
    }

}
