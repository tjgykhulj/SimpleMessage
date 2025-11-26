package com.arnold.msg.metadata.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ClusterMetadata {
    private String name;
    private ClusterKind kind;
    private Map<String, Object> provider;
}
