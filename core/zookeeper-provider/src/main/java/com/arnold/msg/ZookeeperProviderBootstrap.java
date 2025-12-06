package com.arnold.msg;

import com.arnold.msg.metadata.model.*;
import com.arnold.msg.metadata.store.MetadataStoreRegistry;
import com.arnold.msg.metadata.store.ZookeeperMetadataStore;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ZookeeperProviderBootstrap {

    public static void initAll() {
        initClient();
        initMetadataStore();
    }

    public static void initClient() {
        ZookeeperClientHolder.initialize();
    }

    private static void initMetadataStore() {
        for (ResourceType type : ResourceType.values()) {
            MetadataStoreRegistry.registerMetadataStore(type,
                    new ZookeeperMetadataStore<>(type, type.getMetadataClass()));
        }
    }
}
