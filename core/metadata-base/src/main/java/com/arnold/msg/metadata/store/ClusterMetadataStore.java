package com.arnold.msg.metadata.store;

import com.arnold.msg.metadata.model.ClusterMetadata;

import java.util.List;

public interface ClusterMetadataStore {
    ClusterMetadata save(ClusterMetadata clusterMetadata);
    ClusterMetadata findByName(String name);
    List<ClusterMetadata> findAll();
    ClusterMetadata delete(String name);
}
