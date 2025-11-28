package com.arnold.msg.metadata.store;

import com.arnold.msg.metadata.model.Metadata;
import com.arnold.msg.metadata.model.ResourceType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMetadataStore implements MetadataStore<Metadata> {

    private final Map<String, Metadata> metadataMap;

    public synchronized static void initialize() {
        for (ResourceType type : ResourceType.values()) {
            MetadataStoreRegistry.registerMetadataStore(type, new InMemoryMetadataStore());
        }
    }

    private InMemoryMetadataStore() {
        this.metadataMap = new ConcurrentHashMap<>();
    }

    @Override
    public Metadata save(Metadata metadata) {
        metadataMap.put(metadata.getId(), metadata);
        return metadata;
    }

    @Override
    public Metadata delete(String name) {
        return metadataMap.remove(name);
    }

    @Override
    public Metadata findByName(String name) {
        return metadataMap.get(name);
    }
}
