package com.arnold.msg.metadata.operator;

import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.opeartor.QueueBackendOperator;

// TODO
public class KafkaQueueBackendOperator implements QueueBackendOperator {

    @Override
    public QueueMetadata createQueue(QueueMetadata metadata) {
        return null;
    }

    @Override
    public QueueMetadata updateQueue(QueueMetadata metadata) {
        return null;
    }

    @Override
    public QueueMetadata deleteQueue(String name) {
        return null;
    }

    @Override
    public QueueMetadata getQueue(String name) {
        return null;
    }
}
