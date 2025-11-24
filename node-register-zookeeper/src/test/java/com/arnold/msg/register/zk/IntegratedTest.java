package com.arnold.msg.register.zk;

import com.arnold.msg.register.DataNode;
import org.junit.jupiter.api.Test;

public class IntegratedTest {

    @Test
    public void testCase1() throws Exception {
        test(new DataNode("127.0.0.1", 8080));
    }

    @Test
    public void testCase2() {
        test(new DataNode("127.0.0.1", 8081));
    }

    private void test(DataNode node) {
        ZookeeperNodeRegistry registry = new ZookeeperNodeRegistry("127.0.0.1:2181", "/test");
        String path = registry.registerNode(node);

        System.out.println(path);
        int i = System.in.read();
    }
}
