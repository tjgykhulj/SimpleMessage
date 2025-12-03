package com.arnold.msg.server;

import com.arnold.msg.InMemoryProviderBootstrap;
import com.arnold.msg.KafkaProviderBootstrap;
import com.arnold.msg.ZookeeperProviderBootstrap;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ServerBootstrap {

    public static void main(String[] args) throws IOException, InterruptedException {
        initialize();
        new GrpcServiceBootstrap().start();
    }

    private static void initialize() {
        // init zk as meta store
        ZookeeperProviderBootstrap.initAll();
        // init kafka backend data storage
        KafkaProviderBootstrap.initAll();
        // init in-memory backend data storage
        InMemoryProviderBootstrap.initBackendOperator();
        InMemoryProviderBootstrap.initDataClientPool();
    }
}
