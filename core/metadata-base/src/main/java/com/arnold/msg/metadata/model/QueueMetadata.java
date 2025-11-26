package com.arnold.msg.metadata.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class QueueMetadata {
    private String name;
    private String cluster = "test";
    private Map<String, Object> provider;
}
