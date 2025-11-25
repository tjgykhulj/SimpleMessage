package com.arnold.msg.registry;

import com.arnold.msg.registry.exceptions.EmptyHashRingException;
import com.google.common.hash.HashFunction;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ConsistentHashRing {

    private final HashFunction hashFunction;
    private final TreeMap<Long, DataNode> ring;
    private final Map<String, DataNode> nodes;
    private final ReentrantReadWriteLock lock;

    public ConsistentHashRing(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
        this.ring = new TreeMap<>();
        this.nodes = new HashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void refreshAll(Collection<DataNode> allNodes) {
        log.debug("refresh all data nodes {}", allNodes);
        lock.writeLock().lock();
        try {
            ring.clear();
            nodes.clear();
            for (DataNode node: allNodes) {
                registerNode(node);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void registerNode(DataNode node) {
        lock.writeLock().lock();
        try {
            removeNodeIfExist(node);
            for (int i = 0; i < node.getReplicas(); i++) {
                long hash = hashNode(node, i);
                ring.put(hash, node);
            }
            nodes.put(node.getKey(), node);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Calculate and assign a data node for the input key
     */
    public DataNode getNode(String key) throws EmptyHashRingException {
        lock.readLock().lock();
        try {
            if (ring.isEmpty()) {
                throw new EmptyHashRingException();
            }
            long hash = hashKey(key);
            Map.Entry<Long, DataNode> node = ring.ceilingEntry(hash);
            if (node == null) {
                return ring.firstEntry().getValue();
            } else {
                return node.getValue();
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    private void removeNodeIfExist(DataNode node) {
        DataNode existNode = nodes.get(node);
        if (existNode != null) {
            for (int i=0; i<existNode.getReplicas(); i++) {
                long hash = hashNode(node, i);
                ring.remove(hash);
            }
        }
        nodes.remove(node);
    }

    private long hashNode(DataNode node, int index) {
        return hashKey(node.getKey() + ":" + index);
    }

    private long hashKey(String key) {
        return hashFunction.hashString(key, StandardCharsets.UTF_8).asLong();
    }

}
