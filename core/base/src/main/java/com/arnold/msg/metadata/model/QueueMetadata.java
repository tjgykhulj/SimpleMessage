package com.arnold.msg.metadata.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueueMetadata implements Metadata {
    private String id;
    private String cluster = "test";
    private String provider;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.QUEUE;
    }
}
