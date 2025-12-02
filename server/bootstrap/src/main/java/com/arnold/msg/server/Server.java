package com.arnold.msg.server;

import com.arnold.msg.InMemoryProviderBootstrap;
import com.arnold.msg.KafkaProviderBootstrap;
import com.arnold.msg.ZookeeperProviderBootstrap;
import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.model.ResourceType;
import com.arnold.msg.metadata.opeartor.BackendOperator;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;
import com.arnold.msg.metadata.store.MetadataStore;
import com.arnold.msg.metadata.store.MetadataStoreRegistry;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        initialize();
        createMetadata(ClusterKind.KAFKA);
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

    private static void createMetadata(ClusterKind clusterKind) {
        MetadataStore<ClusterMetadata> clusterMetadataStore = MetadataStoreRegistry.getMetadataStore(ResourceType.CLUSTER);
        ClusterMetadata cluster = new ClusterMetadata();
        cluster.setId("test-cluster");
        cluster.setKind(clusterKind);
        cluster.setProvider("{}");
        clusterMetadataStore.save(cluster);

        MetadataStore<QueueMetadata> queueMetadataStore = MetadataStoreRegistry.getMetadataStore(ResourceType.QUEUE);
        QueueMetadata queue = new QueueMetadata();
        queue.setId("test-queue");
        queue.setCluster("test-cluster");
        queue.setProvider("{}");
        queueMetadataStore.save(queue);

        BackendOperator operator = BackendOperatorRegistry.getOperator(clusterKind);
        operator.createQueue(cluster, queue);
    }
}
