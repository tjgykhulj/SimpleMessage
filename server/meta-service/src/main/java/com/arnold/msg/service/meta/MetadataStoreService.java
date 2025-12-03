package com.arnold.msg.service.meta;

import com.arnold.msg.JsonUtils;
import com.arnold.msg.exceptions.MetadataAlreadyExistException;
import com.arnold.msg.exceptions.MetadataNotFoundException;
import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.model.ResourceType;
import com.arnold.msg.metadata.opeartor.BackendOperator;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;
import com.arnold.msg.metadata.store.MetadataStore;
import com.arnold.msg.metadata.store.MetadataStoreRegistry;
import com.arnold.msg.proto.common.ClusterInfo;
import com.arnold.msg.proto.common.ClusterKindEnum;
import com.arnold.msg.proto.common.QueueInfo;

public class MetadataStoreService {

    private static MetadataStoreService INSTANCE;

    private final MetadataStore<QueueMetadata> queueStore;
    private final MetadataStore<ClusterMetadata> clusterStore;

    public static synchronized MetadataStoreService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MetadataStoreService();
        }
        return INSTANCE;
    }

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

    public ClusterInfo createCluster(ClusterInfo clusterInfo) {
        ClusterMetadata existCluster = clusterStore.findByID(clusterInfo.getName());
        if (existCluster != null) {
            throw new MetadataAlreadyExistException(clusterInfo.getName() + " already exist");
        }
        ClusterMetadata metadata = new ClusterMetadata();
        metadata.setId(clusterInfo.getName());
        metadata.setKind(ClusterKind.valueOf(clusterInfo.getKind().name()));
        metadata.setProvider(clusterInfo.getProviderMap());
        getOperator(metadata).validateCluster(metadata);
        clusterStore.save(metadata);
        return clusterInfo;
    }

    public ClusterInfo deleteCluster(String name) {
        ClusterMetadata metadata = clusterStore.deleteByID(name);
        if (metadata == null) {
            throw new MetadataNotFoundException("cluster not found: " + name);
        }
        return ClusterInfo.newBuilder()
                .setName(metadata.getId())
                .setKind(ClusterKindEnum.valueOf(metadata.getKind().name()))
                .putAllProvider(metadata.getProvider())
                .build();
    }

    public QueueInfo createQueue(QueueInfo info) {
        ClusterMetadata cluster = clusterStore.findByID(info.getCluster());
        if (cluster == null) {
            throw new MetadataNotFoundException("Cluster not found: " +info.getCluster());
        }
        QueueMetadata queue = queueStore.findByID(info.getName());
        if (queue != null) {
            throw new MetadataAlreadyExistException("Queue already exist: " + info.getName());
        }
        queue = convertFromProto(info);
        queue.setId(info.getName());
        queue.setCluster(info.getCluster());
        getOperator(cluster).createQueue(cluster, queue);
        queue = queueStore.save(queue);
        // TODO queue metadata may have a provider map which added by operator
        return convertToProto(queue);
    }

    public QueueInfo deleteQueue(String name) {
        ClusterMetadata cluster = findClusterByQueue(name);
        getOperator(cluster).deleteQueue(cluster, name);
        QueueMetadata metadata = queueStore.deleteByID(name);
        return convertToProto(metadata);
    }

    private QueueMetadata convertFromProto(QueueInfo info) {
        if (info == null) {
            return null;
        }
        QueueMetadata queue = new QueueMetadata();
        queue.setId(info.getName());
        queue.setCluster(info.getCluster());
        return queue;
    }

    private QueueInfo convertToProto(QueueMetadata metadata) {
        if (metadata == null) {
            return null;
        }
        return QueueInfo.newBuilder()
                .setName(metadata.getId())
                .setCluster(metadata.getCluster())
                .build();
    }

    private BackendOperator getOperator(ClusterMetadata cluster) {
        return BackendOperatorRegistry.getOperator(cluster.getKind());
    }
}
