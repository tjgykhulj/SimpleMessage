package com.arnold.msg.metadata.opeartor;

import com.arnold.msg.metadata.model.QueueMetadata;

public interface QueueBackendOperator {

    QueueMetadata createQueue(QueueMetadata metadata);

    QueueMetadata updateQueue(QueueMetadata metadata);

    QueueMetadata deleteQueue(String name);

    QueueMetadata getQueue(String name);
}
