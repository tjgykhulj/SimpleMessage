package com.arnold.msg.register.register.zk;

import com.arnold.msg.register.DataNode;

public class Main {

    public static void main(String[] args) throws Exception {
        ZookeeperNodeRegistry registry = new ZookeeperNodeRegistry("127.0.0.1:2181", "/test");
        String path = registry.registerNode(new DataNode("127.0.0.1", 8080));
        System.out.println(path);
        int i = System.in.read();
    }
}
