package com.arnold.msg.metadata.store;

import com.arnold.msg.metadata.model.Metadata;

public interface MetadataStore<T extends Metadata> {
    T save(T metadata);
    T deleteByID(String id);
    T findByID(String id);
}
