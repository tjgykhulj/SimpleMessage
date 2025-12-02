package com.arnold.msg.data;

import com.arnold.msg.metadata.model.ConsumerMetadata;

public class InMemoryMessageClientPool implements MessageClientPool {

    @Override
    public MessageConsumer getConsumer(String consumer) {
        return new InMemoryAtMostOnceMessageConsumer(consumer);
    }

    @Override
    public MessageProducer getProducer(String producer) {
        return new InMemoryMessageProducer();
    }
}
