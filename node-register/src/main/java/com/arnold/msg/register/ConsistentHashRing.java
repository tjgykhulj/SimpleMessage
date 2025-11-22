package com.arnold.msg.register;

import com.arnold.msg.register.exceptions.EmptyRingException;
import com.google.common.hash.HashFunction;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConsistentHashRing {

    private final HashFunction hashFunction;
    private final TreeMap<Long, DataNode> ring;
    private final Map<DataNode, Integer> nodes;
    private final ReentrantReadWriteLock lock;

    public ConsistentHashRing(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
        this.ring = new TreeMap<>();
        this.nodes = new HashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void registerNode(DataNode node, int replicas) {
        lock.writeLock().lock();
        try {
            removeNodeIfExist(node);
            for (int i = 0; i < replicas; i++) {
                long hash = hashNode(node, i);
                ring.put(hash, node);
            }
            nodes.put(node, replicas);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Calculate and assign a data node for the input key
     */
    public DataNode getNode(String key) throws EmptyRingException {
        lock.readLock().lock();
        try {
            if (ring.isEmpty()) {
                throw new EmptyRingException();
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
        Integer replicas = nodes.get(node);
        if (replicas != null) {
            for (int i=0; i<replicas; i++) {
                long hash = hashNode(node, i);
                ring.remove(hash);
            }
        }
        nodes.remove(node);
    }

    private long hashNode(DataNode node, int index) {
        return hashKey(node.toString() + ":" + index);
    }

    private long hashKey(String key) {
        return hashFunction.hashString(key, StandardCharsets.UTF_8).asLong();
    }

}
