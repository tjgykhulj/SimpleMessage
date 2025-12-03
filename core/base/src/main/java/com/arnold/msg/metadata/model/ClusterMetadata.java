package com.arnold.msg.metadata.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClusterMetadata implements Metadata {
    private String id;
    private ClusterKind kind;
    private Map<String, String> provider;

    public ClusterMetadata(ClusterMetadata metadata) {
        this.id = metadata.getId();
        this.kind = metadata.getKind();
        this.provider = metadata.getProvider();
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.CLUSTER;
    }
}
