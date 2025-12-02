package com.arnold.msg.data;

import com.arnold.msg.exceptions.NotInitializeException;
import com.arnold.msg.metadata.model.ClusterKind;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageClientPoolRegistry {

    private static final Map<ClusterKind, MessageClientPool> OPERATOR_MAP = new ConcurrentHashMap<>();

    public static void registerPool(ClusterKind kind, MessageClientPool pool) {
        OPERATOR_MAP.putIfAbsent(kind, pool);
    }

    public static MessageClientPool getPool(ClusterKind kind) {
        if (!OPERATOR_MAP.containsKey(kind)) {
            throw new NotInitializeException("MessageClientPool has not been initialized for " + kind);
        }
        return OPERATOR_MAP.get(kind);
    }
}
