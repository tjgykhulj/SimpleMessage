package com.arnold.msg.data;

import com.arnold.msg.data.model.Message;

public interface MessageProducer {
    boolean send(Message message);
}
