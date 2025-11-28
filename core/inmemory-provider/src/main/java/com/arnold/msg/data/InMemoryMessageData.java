package com.arnold.msg.data;

import com.arnold.msg.data.model.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface InMemoryMessageData {
    Map<String, List<Message>> DATA = new ConcurrentHashMap<>();
    Map<String, Integer> OFFSETS = new ConcurrentHashMap<>();
}
