package com.arnold.msg.metadata.store;

import com.arnold.msg.metadata.model.Metadata;

public interface MetadataStore<T extends Metadata> {

    T save(T metadata);
    T delete(String name);
    T findByName(String name);
}
