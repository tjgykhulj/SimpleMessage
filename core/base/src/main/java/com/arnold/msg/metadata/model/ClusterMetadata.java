package com.arnold.msg.metadata.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClusterMetadata implements Metadata {
    private String id;
    private ClusterKind kind;
    private String provider;

    @Override
    public ResourceType getResourceType() {
        return ResourceType.CLUSTER;
    }
}
