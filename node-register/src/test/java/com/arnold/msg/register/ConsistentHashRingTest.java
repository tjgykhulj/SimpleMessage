package com.arnold.msg.register;

import com.google.common.hash.Hashing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ConsistentHashRingTest {

    private ConsistentHashRing hashRing;

    @BeforeEach
    void setUp() {
        hashRing = new ConsistentHashRing(Hashing.murmur3_128());
    }

    @Test
    void testBasicFunctionality() {
        hashRing.registerNode(new DataNode("192.168.1.1", 8080), 2);
        hashRing.registerNode(new DataNode("192.168.1.1", 8081), 2);

        DataNode node = hashRing.getNode("test-key");
        assertNotNull(node);

        DataNode node1 = hashRing.getNode("consistent-key");
        DataNode node2 = hashRing.getNode("consistent-key");
        assertEquals(node1, node2);
    }
}
