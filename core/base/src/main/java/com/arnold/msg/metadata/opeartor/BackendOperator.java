package com.arnold.msg.metadata.opeartor;

import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;

public interface BackendOperator {

    void createQueue(ClusterMetadata cluster, QueueMetadata queue);

    void deleteQueue(ClusterMetadata cluster, String name);
}
