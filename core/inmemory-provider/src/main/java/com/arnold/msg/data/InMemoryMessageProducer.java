package com.arnold.msg.data;

import com.arnold.msg.data.model.Message;

import static com.arnold.msg.data.InMemoryMessageData.DATA;

public class InMemoryMessageProducer implements MessageProducer {

    @Override
    public boolean send(Message message) {
        String queue = message.getQueue();
        return DATA.get(queue).add(message);
    }
}
