package com.arnold.msg.server;

import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.model.ResourceType;
import com.arnold.msg.metadata.opeartor.BackendOperator;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;
import com.arnold.msg.metadata.operator.InMemoryBackendOperator;
import com.arnold.msg.metadata.store.InMemoryMetadataStore;
import com.arnold.msg.metadata.store.MetadataStore;
import com.arnold.msg.metadata.store.MetadataStoreRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InMemoryServer {
    public static void main(String[] args) {
        InMemoryBackendOperator.initialize();
        InMemoryMetadataStore.initialize();

        MetadataStore<ClusterMetadata> clusterMetadataStore = MetadataStoreRegistry.getMetadataStore(ResourceType.CLUSTER);
        ClusterMetadata cluster = new ClusterMetadata();
        cluster.setId("test");
        cluster.setKind(ClusterKind.IN_MEMORY);
        cluster.setProvider("{}");
        clusterMetadataStore.save(cluster);

        MetadataStore<QueueMetadata> queueMetadataStore = MetadataStoreRegistry.getMetadataStore(ResourceType.QUEUE);
        QueueMetadata queue = new QueueMetadata();
        queue.setId("test");
        queue.setCluster("test");
        queue.setProvider("{}");
        queueMetadataStore.save(queue);

        BackendOperator operator = BackendOperatorRegistry.getOperator(cluster.getKind());
        operator.createQueue(cluster, queue);

    }
}