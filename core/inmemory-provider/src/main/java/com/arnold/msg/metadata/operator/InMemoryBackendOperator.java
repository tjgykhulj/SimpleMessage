package com.arnold.msg.metadata.operator;

import com.arnold.msg.data.InMemoryMessageData;
import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.opeartor.BackendOperator;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;

import java.util.LinkedList;

public class InMemoryBackendOperator implements BackendOperator {

    private static final InMemoryBackendOperator INSTANCE = new InMemoryBackendOperator();

    public static void initialize() {
        BackendOperatorRegistry.registerOperator(ClusterKind.IN_MEMORY, INSTANCE);
    }

    private InMemoryBackendOperator() {
    }

    @Override
    public void createQueue(ClusterMetadata cluster, QueueMetadata queue) {
        InMemoryMessageData.DATA.putIfAbsent(queue.getId(), new LinkedList<>());
    }

    @Override
    public void deleteQueue(ClusterMetadata cluster, String name) {
        InMemoryMessageData.DATA.remove(name);
    }
}
