package com.arnold.msg;

import com.arnold.msg.data.KafkaMessageClientPool;
import com.arnold.msg.data.MessageClientPoolRegistry;
import com.arnold.msg.metadata.KafkaBackendOperator;
import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;

public class KafkaProviderBootstrap {

    public static void initAll() {
        initBackendOperator();
        initDataClientPool();
    }

    public static void initDataClientPool() {
        MessageClientPoolRegistry.registerPool(ClusterKind.KAFKA, new KafkaMessageClientPool());
    }

    public static void initBackendOperator() {
        BackendOperatorRegistry.registerOperator(ClusterKind.KAFKA, new KafkaBackendOperator());
    }
}
