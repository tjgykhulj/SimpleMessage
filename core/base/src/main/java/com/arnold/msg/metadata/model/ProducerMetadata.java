package com.arnold.msg.metadata.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProducerMetadata implements Metadata {
    private String id;
    private String cluster;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.PRODUCER;
    }
}
