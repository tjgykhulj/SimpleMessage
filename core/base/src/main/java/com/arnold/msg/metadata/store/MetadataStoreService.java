package com.arnold.msg.metadata.store;

import com.arnold.msg.exceptions.MetadataNotFoundException;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.model.ResourceType;

public class MetadataStoreService {

    public static final MetadataStoreService INSTANCE = new MetadataStoreService();

    private MetadataStore<QueueMetadata> queueStore;
    private MetadataStore<ClusterMetadata> clusterStore;

    private MetadataStoreService() {
         queueStore = MetadataStoreRegistry.getMetadataStore(ResourceType.QUEUE);
         clusterStore = MetadataStoreRegistry.getMetadataStore(ResourceType.CLUSTER);
    }

    public ClusterMetadata findClusterByQueue(String queueName) {
        QueueMetadata queue = queueStore.findByID(queueName);
        if (queue == null) {
            throw new MetadataNotFoundException("queue not found: " + queueName);
        }
        ClusterMetadata cluster = clusterStore.findByID(queue.getCluster());
        if (cluster == null) {
            throw new MetadataNotFoundException("cluster not found: " + queue.getCluster());
        }
        return cluster;
    }
}
