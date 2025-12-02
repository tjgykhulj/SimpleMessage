package com.arnold.msg.metadata;

import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.opeartor.BackendOperator;

public class KafkaBackendOperator implements BackendOperator {
    @Override
    public void createQueue(ClusterMetadata cluster, QueueMetadata queue) {
        // TODO
    }

    @Override
    public void deleteQueue(ClusterMetadata cluster, String name) {
        // TODO
    }
}
