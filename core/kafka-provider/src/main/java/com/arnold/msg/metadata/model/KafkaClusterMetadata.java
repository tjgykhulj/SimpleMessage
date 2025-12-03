package com.arnold.msg.metadata.model;

import com.arnold.msg.JsonUtils;
import lombok.Getter;

public class KafkaClusterMetadata extends ClusterMetadata {

    @Getter
    private final KafkaClusterConfig config;

    public KafkaClusterMetadata(ClusterMetadata metadata) {
        super(metadata);
        this.config = JsonUtils.fromMapToObj(getProvider(), KafkaClusterConfig.class);
    }
}
