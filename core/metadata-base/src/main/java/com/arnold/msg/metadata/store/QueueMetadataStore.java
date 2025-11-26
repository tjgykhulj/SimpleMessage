package com.arnold.msg.metadata.store;

import com.arnold.msg.metadata.model.QueueMetadata;

public interface QueueMetadataStore {

    QueueMetadata createQueue(QueueMetadata metadata);
    QueueMetadata updateQueue(QueueMetadata metadata);
    QueueMetadata deleteQueue(String name);
    QueueMetadata getQueue(String name);
}
