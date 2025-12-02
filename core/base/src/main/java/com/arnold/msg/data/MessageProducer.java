package com.arnold.msg.data;

import com.arnold.msg.data.model.Message;

import java.util.List;

public interface MessageProducer {
    void send(Message message);
    void send(List<Message> messages);
}
