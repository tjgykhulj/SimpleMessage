package com.arnold.msg.metadata.model;

import lombok.Getter;

public enum ResourceType {
    QUEUE(QueueMetadata.class),
    CLUSTER(ClusterMetadata.class),
    CONSUMER(ConsumerMetadata.class),
    PRODUCER(ProducerMetadata.class);

    @Getter
    private final Class<? extends Metadata> metadataClass;

    ResourceType(Class<? extends Metadata> metadataClass) {
        this.metadataClass = metadataClass;
    }
}
