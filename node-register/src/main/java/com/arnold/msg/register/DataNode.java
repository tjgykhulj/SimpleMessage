package com.arnold.msg.register;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DataNode {
    private String ip;
    private int port;

    public DataNode(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}
