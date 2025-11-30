package com.arnold.msg.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Metadata {

    String getId();
    @JsonIgnore
    ResourceType getResourceType();
}
