package com.arnold.msg.data;

import com.arnold.msg.data.model.Message;

import java.util.List;

import static com.arnold.msg.data.InMemoryMessageData.DATA;

public class InMemoryMessageProducer implements MessageProducer {

    @Override
    public void send(Message message) {
        String queue = message.getQueue();
        DATA.get(queue).add(message);
    }

    @Override
    public void send(List<Message> messages) {
        messages.forEach(this::send);
    }
}
