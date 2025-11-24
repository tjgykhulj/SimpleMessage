package com.arnold.msg.register;

import com.google.common.hash.Hashing;

import java.util.List;

public abstract class NodeRegistry {

    private static ConsistentHashRing RING = new ConsistentHashRing(Hashing.murmur3_128());

    abstract public String registerNode(DataNode node) throws Exception;

    abstract public List<DataNode> getActiveNodes();

    protected void refreshAllDataNodes() {
        RING.refreshAll(getActiveNodes());
    }
}
