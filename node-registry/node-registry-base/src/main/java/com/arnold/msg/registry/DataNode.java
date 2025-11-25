package com.arnold.msg.registry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class DataNode {
    // TODO make it configurable
    private static int DEFAULT_REPLICA_COUNT = 5;

    private String ip;
    private int port;
    private int replicas;

    public DataNode(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.replicas = DEFAULT_REPLICA_COUNT;
    }

    @JsonIgnore
    public String getKey() {
        return ip + ":" + port;
    }
}
